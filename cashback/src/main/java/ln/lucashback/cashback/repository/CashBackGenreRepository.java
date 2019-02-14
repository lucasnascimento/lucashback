package ln.lucashback.cashback.repository;

import ln.lucashback.cashback.model.CashBackTable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CashBackGenreRepository extends MongoRepository<CashBackTable, String> {

}
