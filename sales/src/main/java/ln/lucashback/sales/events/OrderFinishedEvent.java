package ln.lucashback.sales.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFinishedEvent {
    @TargetAggregateIdentifier
    private String orderId;
}
