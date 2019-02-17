package ln.lucashback.sales.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class CacheService {
    static final String CATALOG_CACHE_PREFIX = "catalog/";
    static final String CASHBACK_CACHE_PREFIX = "cashback/";

    @Autowired
    RedisTemplate redisTemplate;

    private ExecutorService executor
            = Executors.newSingleThreadExecutor();

    public Future storeProductOnCache(CatalogResponse catalogResponse) {
        return executor.submit(() -> {
            redisTemplate.opsForValue().set(CATALOG_CACHE_PREFIX + catalogResponse.getProduct().getId(), catalogResponse);
        });
    }

    public CatalogResponse getProductFromCache(String id){
        Object o = redisTemplate.opsForValue().get(CATALOG_CACHE_PREFIX + id);
        if ( o instanceof CatalogResponse)
            return (CatalogResponse) o;

        return null;
    }

    public Future storeCashbackOnCache(String genre, Map<String, Double> cashback) {
        return executor.submit(() -> {
            redisTemplate.opsForValue().set(CATALOG_CACHE_PREFIX + genre, cashback);
        });
    }

    public Map<String, Double> getCashbackCache(String genre){
        Object o = redisTemplate.opsForValue().get(CASHBACK_CACHE_PREFIX + genre);
        if ( o instanceof Map)
            return (Map<String, Double>) o;
        return null;
    }

}
