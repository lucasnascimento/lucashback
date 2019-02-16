package ln.lucashback.sales.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinishOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
}
