package be.hubertrm.dashboard.record.core.testContainer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@Testcontainers
class ContainerLifecycleTest {

    private static final int INTERNAL_PORT = 8080;

    @Container
    private OracleContainer oracleContainer = new OracleContainer(DockerImageName.parse("wnameless/oracle-xe-11g-r2"))
            .withLogConsumer(new Slf4jLogConsumer(log))
            .withExposedPorts(INTERNAL_PORT);

    @Test
    void testLifecycle1(){}

    @Test
    void testLifecycle2(){}

}
