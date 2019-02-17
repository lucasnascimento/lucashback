package ln.lucashback.sales.feign;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import org.apache.commons.io.IOUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Map;

@Aspect
@Component
public class FeignClientAspect {

    @Autowired
    CacheService cacheService;

    @Pointcut("execution(* org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient.*(..))")
    public void feignClientPointcut() {
    }

    @org.aspectj.lang.annotation.Around("ln.lucashback.sales.feign.FeignClientAspect.feignClientPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = joinPoint.proceed();
        if (result instanceof Response) {
            Response response = (Response) result;

            if (response.status() == 200) {
                StringWriter writer = new StringWriter();
                IOUtils.copy(response.body().asInputStream(), writer, "UTF-8");
                String theString = writer.toString();

                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                CatalogResponse catalogResponse = mapper.readValue(theString, CatalogResponse.class);
                if (catalogResponse != null && catalogResponse.getProduct() != null && catalogResponse.getProduct().getId() != null)
                    cacheService.storeProductOnCache(catalogResponse);
                else {
                    TypeReference<Map<String, Double>> typeRef
                            = new TypeReference<Map<String, Double>>() {
                    };
                    Map<String, Double> cashback = mapper.readValue(theString, typeRef);
                    if (cashback != null && cashback.size() > 0) {
                        Object[] args = joinPoint.getArgs();
                        String genre = null;
                        if (args.length == 1) {
                            genre = (String) args[1];
                        }
                        cacheService.storeCashbackOnCache(genre, cashback);
                    }
                }
            }

        }

        return result;
    }
}
