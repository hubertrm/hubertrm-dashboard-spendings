package be.hubertrm.dashboard.record.core.testContainer;

import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = ContainerSpringTest.class)
@EnableAutoConfiguration
public class ContainerSpringTest {

    @Container
    public static OracleContainer oracleXeContainer = new OracleContainer("wnameless/oracle-xe-11g-r2")
            .withUsername("user")
            .withPassword("password");

    @DynamicPropertySource
    public static void oracleXeProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", oracleXeContainer::getJdbcUrl);
        registry.add("spring.datasource.username", oracleXeContainer::getUsername);
        registry.add("spring.datasource.password", oracleXeContainer::getPassword);

        registry.add("spring.liquibase.url", oracleXeContainer::getJdbcUrl);
        registry.add("spring.liquibase.user", oracleXeContainer::getUsername);
        registry.add("spring.liquibase.password", oracleXeContainer::getPassword);
        registry.add("spring.liquibase.change-log", () -> "classpath:db.changelog.xml");
    }

    @Test
    public void testData() throws SQLException {
        oracleXeContainer.start();
        String jdbcUrl = oracleXeContainer.getJdbcUrl();
        String username = oracleXeContainer.getUsername();
        String password = oracleXeContainer.getPassword();
        Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
        ResultSet resultSet = conn.createStatement().executeQuery("SELECT 1 FROM DUAL");
        resultSet.next();
        int result = resultSet.getInt(1);

        assertThat(result).isEqualTo(1);
    }
}
