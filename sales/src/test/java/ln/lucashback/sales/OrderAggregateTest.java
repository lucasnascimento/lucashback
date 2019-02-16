package ln.lucashback.sales;

import ln.lucashback.sales.aggregates.OrderAggregate;
import ln.lucashback.sales.command.CalculateOrderCommand;
import ln.lucashback.sales.command.FinishOrderCommand;
import ln.lucashback.sales.command.PlaceOrderCommand;
import ln.lucashback.sales.events.OrderCalculatedEvent;
import ln.lucashback.sales.events.OrderFinishedEvent;
import ln.lucashback.sales.events.OrderPlacedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(JUnit4.class)
public class OrderAggregateTest {

    private FixtureConfiguration<OrderAggregate> fixture;

    @Before
    public void setup(){
        fixture = new AggregateTestFixture<>(OrderAggregate.class);
    }

    @Test
    public void placeOrderTest(){
        String orderId = UUID.randomUUID().toString();
        String customerName = "Lucas Nascimento";
        List<String> products = Arrays.asList("123","456", "798");

        fixture.givenNoPriorActivity()
                .when(new PlaceOrderCommand(orderId, customerName, products))
                .expectEvents(new OrderPlacedEvent(orderId, customerName, products));

    }

    @Test
    public void finishExceptionOrderTest(){
        String orderId = UUID.randomUUID().toString();
        String customerName = "Lucas Nascimento";
        List<String> products = Arrays.asList("123","456", "798");

        fixture.given(new OrderPlacedEvent(orderId, customerName, products))
                .when(new FinishOrderCommand(orderId))
                .expectException(IllegalStateException.class);
    }

    @Test
    public void finishOrderTest(){
        String orderId = UUID.randomUUID().toString();
        String customerName = "Lucas Nascimento";
        List<String> products = Arrays.asList("123","456", "798");

        fixture.given(new OrderPlacedEvent(orderId, customerName, products), new OrderCalculatedEvent(orderId))
                .when(new FinishOrderCommand(orderId))
                .expectEvents(new OrderFinishedEvent(orderId));
    }


}
