package ln.lucashback.sales.repository;

import ln.lucashback.sales.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;

public interface OrderRepository extends MongoRepository<Order, String> {

    @Query(value = "{'orderDate': {$gte: ?0, $lte:?1 }}", sort = "{'orderDate': -1}")
    Page<Order> findByOrderDateAfterAndOrderDateBeforeOrderByOrderDateDesc(LocalDateTime startPeriod, LocalDateTime endPeriod, Pageable pageable);

}
