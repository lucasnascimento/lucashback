package ln.lucashback.sales.controller;

import ln.lucashback.sales.command.CalculateOrderCommand;
import ln.lucashback.sales.command.FinishOrderCommand;
import ln.lucashback.sales.command.PlaceOrderCommand;
import ln.lucashback.sales.controller.dto.CreateOrderRequest;
import ln.lucashback.sales.model.Order;
import ln.lucashback.sales.queries.FindAllOrdersQuery;
import ln.lucashback.sales.queries.FindOrderQuery;
import ln.lucashback.sales.utils.DateUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static ln.lucashback.sales.utils.DateUtils.getLocalDateTime;

@RestController
public class SalesApi {
    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private QueryGateway queryGateway;

    @PostMapping
    public String placeOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        String orderId = UUID.randomUUID().toString();
        commandGateway.sendAndWait(new PlaceOrderCommand(orderId
                ,createOrderRequest.getCustomerName()
                ,createOrderRequest.getProducts()));
        commandGateway.send(new CalculateOrderCommand(orderId));
        commandGateway.send(new FinishOrderCommand(orderId));

        return orderId;
    }

    @GetMapping
    public Page<Order> listAllOrders(@RequestParam("start-period") String startPeriod,
                                     @RequestParam("end-period") String endPeriod,
                                     Pageable pageable){

        LocalDateTime start = getLocalDateTime(startPeriod);
        LocalDateTime end = getLocalDateTime(endPeriod);

        List<Order> orders = queryGateway.query(new FindAllOrdersQuery(start, end, pageable),
                 ResponseTypes.multipleInstancesOf(Order.class)).join();

        return new PageImpl<>(orders, pageable, orders.size());
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable String id){

        return queryGateway.query(new FindOrderQuery(id), ResponseTypes.instanceOf(Order.class)).join();
    }

}
