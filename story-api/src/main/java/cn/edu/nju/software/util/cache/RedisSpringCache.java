package cn.edu.nju.software.util.cache;

import cn.edu.nju.software.util.ObjectAndByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class RedisSpringCache implements Cache, InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String name;
    private RedisTemplate<String, Object> redisTemplate;


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

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @PreDestroy
    public void destroyMethod() throws Exception {
        //TODO destroy
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(redisTemplate, "redisTemplate must not be null!");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public RedisTemplate getNativeCache() {
        return this.redisTemplate;
    }

    @Override
    public ValueWrapper get(Object key) {
        logger.info("获得Key:{} 的值", getCacheKey(key));
        final String finalKey = getCacheKey(key);
        Object object;
        object = redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key1 = finalKey.getBytes();
            byte[] value = connection.get(key1);
            if (value == null) {
                return null;
            }
            return ObjectAndByte.toObject(value);
        });
        return (object != null ? new SimpleValueWrapper(fromStoreValue(object)) : null);
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
        //TODO 不知道该做点什么
        return null;
    }

    @Override
    public void put(Object key, Object value) {
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
        final Object cacheValue = value;
        final int cacheExpiration = expiration;
        redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            connection.set(cacheKey.getBytes(), ObjectAndByte.toByteArray(cacheValue));
            redisTemplate.expire(cacheKey, cacheExpiration, TimeUnit.SECONDS);
            return true;
        });
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
        final String cacheKey = getCacheKey(key);
        redisTemplate.execute((RedisCallback<Long>) connection -> connection.del(cacheKey.getBytes()));
    }

    @Override
    public void clear() {
        //TODO 清除指定值开头的缓存 目前是清空所有的缓存
        redisTemplate.execute((RedisCallback<String>) connection -> {
            connection.flushDb();
            return "ok";
        });
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
