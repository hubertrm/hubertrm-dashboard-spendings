package be.hubertrm.dashboard.record.core;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles({ "test", "local"})
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@EnableJpaRepositories({"be.hubertrm.dashboard.record"})
@EntityScan({"be.hubertrm.dashboard.record"})
@Slf4j
public abstract class RecordCoreBaseIntegrationTest {

    public static final String API_PATH = "/api/v1";

    @Autowired
    protected MockMvc mvc;

    @BeforeEach
    public void setup(TestInfo testInfo) {
        log.info("=====================================================");
        log.info("Begin Test " + testInfo.getDisplayName());
        log.info("=====================================================");
    }

    @AfterEach
    public void teardown(TestInfo testInfo) {
        log.info("=====================================================");
        log.info("End Test " + testInfo.getDisplayName());
        log.info("=====================================================");
    }
}
