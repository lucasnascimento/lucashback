package ln.lucashback.sales.repository;

import ln.lucashback.sales.model.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, String> {
}
