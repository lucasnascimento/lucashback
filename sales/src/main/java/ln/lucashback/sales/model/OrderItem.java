package ln.lucashback.sales.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
    private String productId;
    private Double productValue;
    private String genre;
    private Double cashbackPercentile;
    private BigDecimal cashbackValue;

    public OrderItem(String productId) {
        this.productId = productId;
    }
}
