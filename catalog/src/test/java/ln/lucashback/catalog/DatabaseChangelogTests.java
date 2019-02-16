package ln.lucashback.catalog;

import ln.lucashback.catalog.repository.DiscoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class DatabaseChangelogTests {

    @MockBean
    DiscoRepository discoRepository;

    @Test
    public void initializeDatabaseTest(){
        given(this.discoRepository.count()).willReturn(0l);
        verify(this.discoRepository, times(200)).save(any());
    }


}
