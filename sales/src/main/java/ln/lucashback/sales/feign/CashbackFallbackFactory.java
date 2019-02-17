package ln.lucashback.sales.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class CashbackFallbackFactory implements FallbackFactory<CashbackClient> {

    @Autowired
    CacheService cacheService;

    @Override
    public CashbackClient create(Throwable cause) {
        return genre -> cacheService.getCashbackCache(genre);
    }
}