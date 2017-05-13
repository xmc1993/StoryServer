package cn.edu.nju.software.vo;

import java.util.Objects;

/**
 * Created by xmc1993 on 16/11/17.
 */
public class CacheKey {
    Integer businessId;
    String platform;

    public CacheKey(Integer businessId, String platform) {
        this.businessId = businessId;
        this.platform = platform;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheKey key = (CacheKey) o;
        return Objects.equals(businessId, key.businessId) &&
                Objects.equals(platform, key.platform);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessId, platform);
    }
}
