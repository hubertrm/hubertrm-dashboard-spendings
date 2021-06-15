package be.hubertrm.dashboard.record.core.spring;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles({"test"})
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = PersistenceTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(value = {
        "classpath:application.properties",
        "classpath:application-test.properties"
})
public abstract class DatabaseTestConfig {

    @ClassRule
    public static TestOracleXeContainer oracleXeContainer = TestOracleXeContainer.getInstance();
}
