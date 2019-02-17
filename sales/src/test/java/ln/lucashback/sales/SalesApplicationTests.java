package ln.lucashback.sales;

import ln.lucashback.sales.feign.CashbackClient;
import ln.lucashback.sales.feign.CatalogClient;
import ln.lucashback.sales.feign.CatalogResponse;
import ln.lucashback.sales.feign.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SalesApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CatalogClient catalogClient;

	@MockBean
	private CashbackClient cashbackClient;

	@Test
	public void placeNewOrderTest() throws Exception {
		given(catalogClient.getProductById(anyString())).willReturn(getMockedProduct());
		given(cashbackClient.getCashbackByGenre(anyString())).willReturn(getMockedCashback());

		this.mvc.perform(post("/")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"customerName\": \"Lucas Nascimento\",\n" +
						"\t\"products\": [\"123\",\"123\",\"123\"]\n" + //3 vezes o mesmo produto ;)
						"}"))
				.andDo(print())
				.andExpect(status().is2xxSuccessful());

	}

	private Map<String, Double> getMockedCashback() {
		Map<String, Double> pop = new HashMap<>();
		pop.put("SUN", 0.10);
		pop.put("MON", 0.20);
		pop.put("TUE", 0.30);
		pop.put("WED", 0.40);
		pop.put("THU", 0.50);
		pop.put("FRI", 0.60);
		pop.put("SAT", 0.70);
		return pop;
	}

	private CatalogResponse getMockedProduct() {
		CatalogResponse catalogResponse = new CatalogResponse();

		Product product = new Product();
		product.setCreator("Mocked Creator");
		product.setId("123");
		product.setGenre("POP");
		product.setTitle("Mocked Title");
		product.setPrice(100d);
		catalogResponse.setProduct(product);

		return catalogResponse;
	}

//	@Test
//	public void contextLoads() {
//	}

}

