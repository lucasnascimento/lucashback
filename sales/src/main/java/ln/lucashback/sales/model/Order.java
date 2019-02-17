package ln.lucashback.sales.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class Order {

    static String[] WEEK_DAYS = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};

    @Id
    private String orderId;

    private String customerName;

    private Double orderTotal;

    private Double cashBackTotal;

    private List<OrderItem> items;

    private OrderStatus status = OrderStatus.PLACED;

    private LocalDateTime orderDate;

    public void setOrderCalculated(){
        this.status = OrderStatus.CALCULATED;
    }

    public void setOrderFinished(){
        this.status = OrderStatus.FINISHED;
    }

    public void setOrderInvalid() {
        this.status = OrderStatus.INVALID;
    }

    public String getWeekDayRepresentation(){
        int ordinal = orderDate.getDayOfWeek().ordinal();
        return WEEK_DAYS[ordinal];
    }

}
