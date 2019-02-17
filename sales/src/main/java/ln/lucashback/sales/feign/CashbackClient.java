package ln.lucashback.sales.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "cashback", fallbackFactory = CashbackFallbackFactory.class)
public interface CashbackClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{genre}")
    Map<String, Double> getCashbackByGenre(@PathVariable("genre") String genre);

}

