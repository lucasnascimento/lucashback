package ln.lucashback.sales.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Order {

    @Id
    private String orderId;

    private String customerName;

    private Double orderTotal;

    private Double cashBackTotal;

    private List<OrderItem> items;

    private OrderStatus status = OrderStatus.PLACED;

    public void setOrderCalculated(){
        this.status = OrderStatus.CALCULATED;
    }

    public void setOrderFinished(){
        this.status = OrderStatus.FINISHED;
    }

}
