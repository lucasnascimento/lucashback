package ln.lucashback.catalog;

import ln.lucashback.catalog.model.Disco;
import ln.lucashback.catalog.repository.DiscoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CatalogApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private DiscoRepository discoRepository;

	@Test
	public void catalogByGenre() throws Exception {
		this.mvc.perform(get("/catalog/genre/MPB"))
				.andExpect(status().isOk());
	}

	@Test
	public void catalogById() throws Exception {
		Disco disco = discoRepository.findAll().stream().findFirst().get();

		this.mvc.perform(get("/catalog/" + disco.getId()))
				.andExpect(status().isOk());
	}


	@Test
	public void catalogNotFound() throws Exception {

		this.mvc.perform(get("/catalog/ABCXXXXX"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void contextLoads() {
	}

}

