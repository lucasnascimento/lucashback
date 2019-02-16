package ln.lucashback.catalog.api.dto;

import ln.lucashback.catalog.model.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class ProductCashBackDTO {
    private Product product;
    private Map<String, Double> cashBackInfo;
}
