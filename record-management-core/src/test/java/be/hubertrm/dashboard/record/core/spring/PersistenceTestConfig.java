package be.hubertrm.dashboard.record.core.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories({"be.hubertrm.dashboard.record"})
@EntityScan({"be.hubertrm.dashboard.record"})
@SpringBootApplication
public class PersistenceTestConfig {
    public static void main(String[] args) {
        SpringApplication.run(PersistenceTestConfig.class, args);
    }
}
