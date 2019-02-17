package ln.lucashback.sales.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class CatalogFallbackFactory implements FallbackFactory<CatalogClient> {

    @Autowired
    CacheService cacheService;

    @Override
    public CatalogClient create(Throwable cause) {
        return productId -> cacheService.getProductFromCache(productId);
    }
}