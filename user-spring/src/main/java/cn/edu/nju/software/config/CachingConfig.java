package cn.edu.nju.software.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

//@Configuration
//@EnableCaching
public class CachingConfig {
    @Bean
    public CacheManager cacheManager(){
        return new ConcurrentMapCacheManager("appVersion");
    }
}
