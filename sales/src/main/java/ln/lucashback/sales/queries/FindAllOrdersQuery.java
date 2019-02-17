package ln.lucashback.sales.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindAllOrdersQuery {
    private LocalDateTime startPeriod;
    private LocalDateTime endPeriod;
    private Pageable pageable;
}
