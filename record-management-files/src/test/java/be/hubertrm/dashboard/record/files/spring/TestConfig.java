package be.hubertrm.dashboard.record.files.spring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestConfig {

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
