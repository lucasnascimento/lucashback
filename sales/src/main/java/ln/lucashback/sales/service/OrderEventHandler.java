package ln.lucashback.sales.service;

import ln.lucashback.sales.events.OrderCalculatedEvent;
import ln.lucashback.sales.events.OrderFinishedEvent;
import ln.lucashback.sales.events.OrderPlacedEvent;
import ln.lucashback.sales.model.Order;
import ln.lucashback.sales.model.OrderItem;
import ln.lucashback.sales.queries.FindAllOrdersQuery;
import ln.lucashback.sales.queries.FindOrderQuery;
import ln.lucashback.sales.repository.OrderRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderEventHandler {

    @Autowired
    OrderRepository orderRepository;

    @EventHandler
    public void on(OrderPlacedEvent event) {
        String orderId = event.getOrderId();
        Order order = new Order();
        order.setOrderId(orderId);
        order.setCustomerName(event.getCustomerName());
        List<OrderItem> orderItems = event.getProducts().stream()
                .map(OrderItem::new)
                .collect(Collectors.toList());
        order.setItems(orderItems);
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCalculatedEvent event) {
        String orderId = event.getOrderId();
        orderRepository.findById(orderId)
                .map(this::calculateOrder)
                .ifPresent(orderRepository::save);
    }

    private Order calculateOrder(Order order) {
        //TODO: Escrever procedimento de cálculo
         order.setOrderCalculated();
        return order;
    }

    @EventHandler
    public void on(OrderFinishedEvent event) {
        String orderId = event.getOrderId();
        orderRepository.findById(orderId)
                .map(this::finishOrder)
                .ifPresent(orderRepository::save);
    }

    private Order finishOrder(Order order) {
        order.setOrderFinished();
        return order;
    }

    @QueryHandler
    public Page<Order> handle(FindAllOrdersQuery query) {
        return orderRepository.findAll(query.getPageable());
    }

    @QueryHandler
    public Order handle(FindOrderQuery query) {
        return orderRepository.findById(query.getOrderId()).get();
    }

}
