package ln.lucashback.cashback;

import ln.lucashback.cashback.changelogs.DatabaseChangelog;
import ln.lucashback.cashback.repository.CashBackGenreRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseChangelogTests {

    @Autowired
    DatabaseChangelog databaseChangelog;

    @MockBean
    CashBackGenreRepository cashBackGenreRepository;

    @Test
    public void initializeDatabaseTest(){
        given(this.cashBackGenreRepository.count()).willReturn(0l);
        databaseChangelog.initializeDatabase();
        verify(this.cashBackGenreRepository, times(2)).save(any());
    }


}
