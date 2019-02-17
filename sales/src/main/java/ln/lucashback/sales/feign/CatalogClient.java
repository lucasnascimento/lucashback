package ln.lucashback.sales.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "catalog", fallbackFactory = CatalogFallbackFactory.class)
public interface CatalogClient {

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    CatalogResponse getProductById(@PathVariable("id") String productId);

}

