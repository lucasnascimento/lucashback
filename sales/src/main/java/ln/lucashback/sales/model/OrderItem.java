package ln.lucashback.sales.model;

import lombok.Data;

@Data
public class OrderItem {
    private String productId;
    private Double productValue;
    private Double cashBackPercentile;

    public OrderItem(String productId) {
        this.productId = productId;
    }
}
