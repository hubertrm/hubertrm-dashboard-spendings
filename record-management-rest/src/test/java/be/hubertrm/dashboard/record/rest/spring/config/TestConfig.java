package be.hubertrm.dashboard.record.rest.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TestConfig {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(TestInfo testInfo) {
        System.out.println("=====================================================");
        System.out.println("Begin Test " + testInfo.getDisplayName());
        System.out.println("=====================================================");
    }

    @AfterEach
    public void teardown(TestInfo testInfo) {
        System.out.println("=====================================================");
        System.out.println("End Test " + testInfo.getDisplayName());
        System.out.println("=====================================================");
    }

}
