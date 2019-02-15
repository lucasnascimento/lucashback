package ln.lucashback.catalog.repository;

import ln.lucashback.catalog.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
