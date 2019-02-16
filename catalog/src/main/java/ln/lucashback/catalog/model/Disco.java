package ln.lucashback.catalog.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = Product.COLLECTION_PRODUCTS)
@NoArgsConstructor
public class Disco extends Product {
    private GenreEnum genre;
    private String title;
    private String creator;

    @Builder
    public Disco(Double price, GenreEnum genre, String title, String creator){
        super(ProductEnum.DISCO, price);
        this.genre = genre;
        this.title = title;
        this.creator = creator;
    }

}
