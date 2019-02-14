package ln.lucashback.cashback.changelogs;

import ln.lucashback.cashback.model.CashBackTable;
import ln.lucashback.cashback.model.DayEnum;
import ln.lucashback.cashback.model.GenreEnum;
import ln.lucashback.cashback.repository.CashBackGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


/**
 * Database initialization
 */
@Component
public class DatabaseChangelog{

    @Autowired
    CashBackGenreRepository cashBackGenreRepository;

    @PostConstruct
    public void initializeDatabase() {
        if (cashBackGenreRepository.count() == 0) {
            Map<DayEnum, Double> pop = new HashMap<>();
            pop.put(DayEnum.SUN, 0.25);
            pop.put(DayEnum.MON, 0.07);
            pop.put(DayEnum.TUE, 0.06);
            pop.put(DayEnum.WED, 0.02);
            pop.put(DayEnum.THU, 0.10);
            pop.put(DayEnum.FRI, 0.15);
            pop.put(DayEnum.SAT, 0.20);

            Map<DayEnum, Double> mpb = new HashMap<>();
            mpb.put(DayEnum.SUN, 0.30);
            mpb.put(DayEnum.MON, 0.05);
            mpb.put(DayEnum.TUE, 0.10);
            mpb.put(DayEnum.WED, 0.15);
            mpb.put(DayEnum.THU, 0.20);
            mpb.put(DayEnum.FRI, 0.25);
            mpb.put(DayEnum.SAT, 0.30);

            Map<DayEnum, Double> classic = new HashMap<>();
            classic.put(DayEnum.SUN, 0.35);
            classic.put(DayEnum.MON, 0.03);
            classic.put(DayEnum.TUE, 0.05);
            classic.put(DayEnum.WED, 0.08);
            classic.put(DayEnum.THU, 0.13);
            classic.put(DayEnum.FRI, 0.18);
            classic.put(DayEnum.SAT, 0.25);

            Map<DayEnum, Double> rock = new HashMap<>();
            rock.put(DayEnum.SUN, 0.40);
            rock.put(DayEnum.MON, 0.10);
            rock.put(DayEnum.TUE, 0.15);
            rock.put(DayEnum.WED, 0.15);
            rock.put(DayEnum.THU, 0.15);
            rock.put(DayEnum.FRI, 0.20);
            rock.put(DayEnum.SAT, 0.40);

            Map<GenreEnum, Map<DayEnum, Double>> cashBackPercentile = new HashMap<>();
            cashBackPercentile.put(GenreEnum.POP, pop);
            cashBackPercentile.put(GenreEnum.MPB, mpb);
            cashBackPercentile.put(GenreEnum.CLASSIC, classic);
            cashBackPercentile.put(GenreEnum.ROCK, rock);

            CashBackTable myEntity = new CashBackTable();
            myEntity.setCashBackPercentile(cashBackPercentile);

            cashBackGenreRepository.save(myEntity);
        }
    }
}
