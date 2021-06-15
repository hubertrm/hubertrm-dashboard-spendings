package be.hubertrm.dashboard.record.core.spring;

import org.testcontainers.containers.OracleContainer;

public class TestOracleXeContainer extends OracleContainer {

    private static final String IMAGE_VERSION = "wnameless/oracle-xe-11g-r2";
    private static TestOracleXeContainer container;

    private TestOracleXeContainer(String dockerImageName) {
        super(dockerImageName);
    }

    public static TestOracleXeContainer getInstance() {
        if (container == null) {
            container = new TestOracleXeContainer(IMAGE_VERSION);
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {

    }
}
