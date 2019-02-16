package ln.lucashback.sales.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String customerName;
    private List<String> products;
}
