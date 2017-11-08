package cn.edu.nju.software.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xmc1993 on 16/11/15.
 */
public class JedisUtil {
    protected static ReentrantLock lockJedis = new ReentrantLock();
    private static JedisPool jedisPool = null;
    private String host = "127.0.0.1";
    private int port = 6379;
    private int timeout = 2000;
    private String password = null;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(15);
        jedisPoolConfig.setMaxTotal(150);
        // 每1小时扫描去除无效连接
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(60*60*1000L);
        jedisPoolConfig.setTestOnBorrow(true);
        new JedisUtil(jedisPoolConfig);
    }


    private JedisUtil(JedisPoolConfig jedisPoolConfig, String host, int port, int timeout, String password) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        if (StringUtil.isEmpty(password)) {
            password = null;
        }
        this.password = password;
        jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
    }

    private JedisUtil(JedisPoolConfig jedisPoolConfig) {
        jedisPool = new JedisPool(jedisPoolConfig, this.host, this.port, this.timeout, this.password);
    }

    public static Jedis getJedis() {
        assert !lockJedis.isHeldByCurrentThread();
        lockJedis.lock();

        if (jedisPool == null) {
            throw new RuntimeException("You should init the pool");
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            throw new RuntimeException("Get jedis error : " + e);
        } finally {
            lockJedis.unlock();
        }
        return jedis;
    }

}
