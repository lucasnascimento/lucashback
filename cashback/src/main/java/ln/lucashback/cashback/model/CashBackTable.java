package ln.lucashback.cashback.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Map;

@Data
public class CashBackTable {
    @Id
    private String id;
    private Map<GenreEnum, Map<DayEnum, Double>> cashBackPercentile;
}

