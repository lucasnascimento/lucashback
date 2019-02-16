package ln.lucashback.sales.aggregates;

import ln.lucashback.sales.command.CalculateOrderCommand;
import ln.lucashback.sales.command.FinishOrderCommand;
import ln.lucashback.sales.command.PlaceOrderCommand;
import ln.lucashback.sales.events.OrderCalculatedEvent;
import ln.lucashback.sales.events.OrderFinishedEvent;
import ln.lucashback.sales.events.OrderPlacedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private boolean orderCalculated;

    protected OrderAggregate() { }

    @CommandHandler
    public OrderAggregate(PlaceOrderCommand command) {
        apply(new OrderPlacedEvent(command.getOrderId(), command.getCustomerName(), command.getProducts()));
    }

    @EventSourcingHandler
    public void on(OrderPlacedEvent event) {
        this.orderId = event.getOrderId();
        orderCalculated = false;
    }

    @CommandHandler
    public void handle(CalculateOrderCommand command) {
        apply(new OrderCalculatedEvent(orderId));
    }

    @EventSourcingHandler
    public void on(OrderCalculatedEvent event) {
        this.orderId = event.getOrderId();
        orderCalculated = true;
    }


    @CommandHandler
    public void handle(FinishOrderCommand command) {
        if (!orderCalculated) {
            throw new IllegalStateException("Cannot finish an order which has not been calculated yet.");
        }
        apply(new OrderFinishedEvent(orderId));
    }

    @EventSourcingHandler
    public void on(OrderFinishedEvent event) {

    }
}
