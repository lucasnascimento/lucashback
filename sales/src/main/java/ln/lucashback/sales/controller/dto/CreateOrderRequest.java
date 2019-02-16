package ln.lucashback.sales.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    private String customerName;
    private List<String> products;
}
