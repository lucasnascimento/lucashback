package ln.lucashback.sales.feign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product implements Serializable {
    private String id;
    private Double price;
    private String genre;
    private String title;
    private String creator;
}
