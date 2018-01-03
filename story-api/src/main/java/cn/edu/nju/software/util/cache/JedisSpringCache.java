package cn.edu.nju.software.util.cache;

import cn.edu.nju.software.util.JedisUtil;
import cn.edu.nju.software.util.ObjectAndByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import redis.clients.jedis.Jedis;

import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.concurrent.Callable;

public class JedisSpringCache implements Cache, InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Jedis jedis;
    private String name;

    /**
     * 默认最长缓存时间为1小时
     */
    private static final int MAX_EXPIRED_DURATION = 60 * 60;

    /**
     * Null值的最长缓存时间
     */
    private static final int NULL_VALUE_EXPIRATION = 60 * 60 * 24 * 7;

    /**
     * 增量过期时间允许设置的最大值
     */
    private static final int DELTA_EXPIRATION_THRESHOLD = 60 * 60 * 24 * 30;

    /**
     * 缓存数据超时时间
     */
    private int expiredDuration = MAX_EXPIRED_DURATION;

    private static final Object NULL_HOLDER = new NullHolder();

    private boolean allowNullValues = false;


    @PreDestroy
    public void destroyMethod() throws Exception {
        releaseLocalJedis();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        Assert.notNull(jedis, "jeids must not be null!");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Jedis getNativeCache() {
        setLocalJedis();
        return this.jedis;
    }

    /**
     * 获取本地的Jedis连接
     */
    private void setLocalJedis(){
        if(this.jedis == null){
            this.jedis = JedisUtil.getJedis();
        }
    }

    /**
     * 释放本地的Jedis连接
     */
    private void releaseLocalJedis(){
        if (this.jedis != null) {
            jedis.close();
            this.jedis = null;
        }
    }

    @Override
    public ValueWrapper get(Object key) {
        setLocalJedis();
        String cacheKey = getCacheKey(key);
        try {
            byte[] value = jedis.get(cacheKey.getBytes());
            logger.info("读取jedis用时{}, key={}", "", cacheKey);
            return (ObjectAndByte.toObject(value) != null ? new SimpleValueWrapper(fromStoreValue(ObjectAndByte.toObject(value))) : null);
        } catch (Exception e) {
            logger.error("读取jedis缓存发生异常, key={}, server={}", cacheKey,
                    jedis.getClient().getHost(), e.getCause());
            return null;
        }finally {
            releaseLocalJedis();
        }
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        ValueWrapper element = get(key);
        Object value = (element != null ? element.get() : null);
        if (value == null)
            return null;
        if (type != null && !type.isInstance(value)) {
            throw new IllegalStateException("缓存的值类型指定错误 [" + type.getName() + "]: " + value);
        }
        return (T) value;
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        setLocalJedis();
        String cacheKey = getCacheKey(key);
        logger.info("放入缓存的Key:{}, Value:{}, StoreValue:{}", cacheKey, value, toStoreValue(value));
        int expiration = expiredDuration;
        if (value == null) {
            if (allowNullValues) {
                value = NULL_HOLDER; // 若允许缓存空值，则替换null为占坑对象；不允许直接缓存null，因为无法序列化
            }
            if (expiredDuration > NULL_VALUE_EXPIRATION) {
                expiration = NULL_VALUE_EXPIRATION; // 缩短空值的过期时间，最长缓存7天
            }
        } else if (expiredDuration > DELTA_EXPIRATION_THRESHOLD) {
            // 修改为UNIX时间戳类型的过期时间，使能够设置超过30天的过期时间
            // 注意：时间戳计算这里有2038问题，
            // 2038-1-19 11:14:07 (GMT +8) 后，转换成的 int 会溢出，导致出现负值
            expiration += (int) (System.currentTimeMillis() / 1000);
        }

        try {
            jedis.set(cacheKey.getBytes(), ObjectAndByte.toByteArray(value));
            jedis.expire(cacheKey.getBytes(), expiration);
        } catch (Exception e) {
            logger.error("jedis写入缓存发生异常, key={}, server={}", cacheKey,
                    jedis.getClient().getHost(), e);
        }finally {
            releaseLocalJedis();
        }
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {

        if (get(key) != null) {
            return null;
        }
        this.put(key, value);
        return this.get(key);
    }

    @Override
    public void evict(Object key) {
        setLocalJedis();
        try {
            jedis.del(getCacheKey(key));
        } catch (Exception e) {
            logger.error("jedis执行flush出现异常", e);
        }finally {
            releaseLocalJedis();
        }
    }

    @Override
    public void clear() {
        //TODO 清除指定值开头的缓存
        setLocalJedis();
        try {
            jedis.flushAll();
        } catch (Exception e) {
            logger.error("jedis执行flush出现异常", e);
        }finally {
            releaseLocalJedis();
        }
    }

    protected Object fromStoreValue(Object storeValue) {
        if (this.allowNullValues && storeValue instanceof NullHolder) {
            return null;
        }
        return storeValue;
    }

    private static class NullHolder implements Serializable {
        private static final long serialVersionUID = -99681708140860560L;
    }

    protected Object toStoreValue(Object userValue) {
        if (this.allowNullValues && userValue == null) {
            return NULL_HOLDER;
        }
        return userValue;
    }

    private String getCacheKey(Object key) {
        return this.name + "-" + key.toString();
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpiredDuration() {
        return expiredDuration;
    }

    public void setExpiredDuration(int expiredDuration) {
        this.expiredDuration = expiredDuration;
    }

    public boolean isAllowNullValues() {
        return allowNullValues;
    }

    public void setAllowNullValues(boolean allowNullValues) {
        this.allowNullValues = allowNullValues;
    }
}
