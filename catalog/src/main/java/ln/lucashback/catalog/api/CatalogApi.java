package ln.lucashback.catalog.api;

import ln.lucashback.catalog.api.dto.ProductCashBackDTO;
import ln.lucashback.catalog.exception.DiscoNotFoundException;
import ln.lucashback.catalog.model.GenreEnum;
import ln.lucashback.catalog.model.Product;
import ln.lucashback.catalog.repository.DiscoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class CatalogApi {

    @Autowired
    DiscoRepository discoRepository;

    @GetMapping("/genre/{genre}")
    public Page<ProductCashBackDTO> listDiscoByGenre(@PathVariable GenreEnum genre, Pageable pageable){
        return discoRepository
                .findByGenre(genre, pageable)
                .map(this::toDTO);
    }

    @GetMapping("/{id}")
    public ProductCashBackDTO getProductById(@PathVariable String id){

        Optional<ProductCashBackDTO> productCashBackDTO = discoRepository
                .findById(id)
                .map(this::toDTO);

        if (productCashBackDTO.isPresent()){
            return productCashBackDTO.get();
        }

        throw new DiscoNotFoundException(id);
    }

    private ProductCashBackDTO toDTO(Product product){
        Map<String, Double> pop = new HashMap<>();
        pop.put("SUN", 0.25);
        pop.put("MON", 0.07);
        pop.put("TUE", 0.06);
        pop.put("WED", 0.02);
        pop.put("THU", 0.10);
        pop.put("FRI", 0.15);
        pop.put("SAT", 0.20);
        return ProductCashBackDTO.builder()
                .product(product)
                .cashBackInfo(pop)
                .build();
    }

}
