package ln.lucashback.cashback;

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
public class CashBackTableApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void cashbackGenreDayTest() throws Exception {
		this.mvc.perform(get("/cashback/POP/THU/")).andExpect(status().isOk())
				.andExpect(content().string("0.1"));
	}

	@Test
	public void cashbackGenreTest() throws Exception {
		this.mvc.perform(get("/cashback/POP/"))
				.andExpect(status().isOk());
	}

	@Test
	public void cashbackTest() throws Exception {
		this.mvc.perform(get("/cashback/"))
				.andExpect(status().isOk());
	}


	@Test
	public void exampleTest() throws Exception {
		this.mvc.perform(get("/cashback/POP/THU/")).andExpect(status().isOk())
				.andExpect(content().string("0.1"));
	}


	@Test
	public void contextLoads() {
	}

}

