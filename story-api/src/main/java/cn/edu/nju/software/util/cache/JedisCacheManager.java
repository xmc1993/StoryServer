package cn.edu.nju.software.util.cache;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

public class JedisCacheManager extends AbstractCacheManager {
	
	public JedisCacheManager() {
    }
     
    private Collection<? extends Cache> caches;
 
    /**
     * Specify the collection of Cache instances to use for this CacheManager.
     */
    public void setCaches(Collection<? extends Cache> caches) {
        this.caches = caches;
    }
 
    @Override
    protected Collection<? extends Cache> loadCaches() {
        return this.caches;
    }
	
}