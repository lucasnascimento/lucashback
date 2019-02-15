package ln.lucashback.catalog.changelog;

import ln.lucashback.catalog.model.Disco;
import ln.lucashback.catalog.model.GenreEnum;
import ln.lucashback.catalog.repository.DiscoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@Component
public class DatabaseChangelog {

    @Autowired
    DiscoRepository discoRepository;

    @PostConstruct
    public void initializeDatabase() {
        if (discoRepository.count() == 0) {
            IntStream.rangeClosed(1, 200)
                    .mapToObj(this::createRandomDisco)
                    .forEach(discoRepository::save);
        }
    }

    private Disco createRandomDisco(Integer i) {
        Double[] priceGuess = {9.90, 19.90, 29.90};
        return Disco.builder()
                .price(priceGuess[i % 3])
                .genre(GenreEnum.values()[(i % 4)])
                .title("Title number -> " + i)
                .creator("Creator number ->" + i)
                .build();
    }

}
