package ln.lucashback.catalog.repository;

import ln.lucashback.catalog.model.Disco;
import ln.lucashback.catalog.model.GenreEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface DiscoRepository extends MongoRepository<Disco, String> {
    Page<Disco> findByGenre(GenreEnum genre, Pageable pageable);
}
