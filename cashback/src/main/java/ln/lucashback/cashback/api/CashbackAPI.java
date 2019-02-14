package ln.lucashback.cashback.api;

import ln.lucashback.cashback.model.DayEnum;
import ln.lucashback.cashback.model.GenreEnum;
import ln.lucashback.cashback.repository.CashBackGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("cashback")
public class CashbackAPI {

    @Autowired
    CashBackGenreRepository cashBackGenreRepository;

    @GetMapping()
    public Map<GenreEnum, Map<DayEnum, Double>> listGenres(){
        Map<GenreEnum, Map<DayEnum, Double>> cashBackPercentile = cashBackGenreRepository.findAll().stream().findFirst().get().getCashBackPercentile();
        return cashBackPercentile;
    }

    @GetMapping("/{genre}")
    public  Map<DayEnum, Double> listdays(@PathVariable("genre") GenreEnum genreEnum){
        Map<GenreEnum, Map<DayEnum, Double>> cashBackPercentile = cashBackGenreRepository.findAll().stream().findFirst().get().getCashBackPercentile();
        return cashBackPercentile.get(genreEnum);
    }

    @GetMapping("/{genre}/{week-day}")
    public double getPercentile(@PathVariable("genre") GenreEnum genreEnum, @PathVariable("week-day") DayEnum dayEnum ){
        Map<GenreEnum, Map<DayEnum, Double>> cashBackPercentile = cashBackGenreRepository.findAll().stream().findFirst().get().getCashBackPercentile();
        return cashBackPercentile.get(genreEnum).get(dayEnum);
    }

}
