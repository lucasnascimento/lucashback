package ln.lucashback.sales.service;

import ln.lucashback.sales.events.OrderCalculatedEvent;
import ln.lucashback.sales.events.OrderFinishedEvent;
import ln.lucashback.sales.events.OrderPlacedEvent;
import ln.lucashback.sales.feign.CacheService;
import ln.lucashback.sales.feign.CashbackClient;
import ln.lucashback.sales.feign.CatalogClient;
import ln.lucashback.sales.feign.CatalogResponse;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderEventHandler {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CatalogClient catalogClient;

    @Autowired
    CashbackClient cashbackClient;

    @Autowired
    CacheService cacheService;


    @EventHandler
    public void on(OrderPlacedEvent event) {
        String orderId = event.getOrderId();
        Order order = new Order();
        order.setOrderId(orderId);
        order.setCustomerName(event.getCustomerName());
        order.setOrderDate(LocalDateTime.now());
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
        order.setOrderCalculated();

        order.getItems().stream().forEach(orderItem -> {
            String productId = orderItem.getProductId();
            CatalogResponse productById = catalogClient.getProductById(productId);

            if (productById != null) {
                cacheService.storeProductOnCache(productById);
                orderItem.setProductValue(productById.getProduct().getPrice());
                orderItem.setGenre(productById.getProduct().getGenre());

                Map<String, Double> cashbackByGenre = cashbackClient.getCashbackByGenre(orderItem.getGenre());
                cacheService.storeCashbackOnCache(orderItem.getGenre(), cashbackByGenre);
                String weekDay = order.getWeekDayRepresentation();
                Double percentile = cashbackByGenre.get(weekDay);
                orderItem.setCashbackPercentile(percentile);
                orderItem.setCashbackValue(BigDecimal.valueOf(orderItem.getProductValue() * orderItem.getCashbackPercentile()).setScale(2, RoundingMode.HALF_UP));
            } else {
                order.setOrderInvalid();
            }
        });

        double totalCashback = order.getItems().stream()
                .map(OrderItem::getCashbackValue)
                .map(bigDecimal -> bigDecimal.setScale(2, RoundingMode.HALF_UP))
                .map(BigDecimal::doubleValue)
                .mapToDouble(Double::new)
                .sum();
        order.setCashBackTotal(totalCashback);

        double totalOrder = order.getItems().stream().mapToDouble(OrderItem::getProductValue).sum();
        order.setOrderTotal(totalOrder);

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
        return orderRepository.findByOrderDateAfterAndOrderDateBeforeOrderByOrderDateDesc(query.getStartPeriod()
                , query.getEndPeriod()
                , query.getPageable());
    }

    @QueryHandler
    public Order handle(FindOrderQuery query) {
        return orderRepository.findById(query.getOrderId()).get();
    }

}
