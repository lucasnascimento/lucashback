package ln.lucashback.catalog.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = Disco.class, name = "DISCO"),
        @JsonSubTypes.Type(value = Product.class, name = "null")})
@NoArgsConstructor
public class Product {
    public static final String COLLECTION_PRODUCTS = "Products";
    @Id
    private String id;
    private Double price;
    private ProductEnum type;

    public Product(ProductEnum type, Double price){
        this.type = type;
        this.price = price;
    }
}
