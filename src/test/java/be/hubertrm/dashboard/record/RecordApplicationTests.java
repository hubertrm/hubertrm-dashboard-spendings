package be.hubertrm.dashboard.record;

import be.hubertrm.dashboard.record.controller.RecordController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RecordApplicationTests {

	@Autowired
	private RecordController recordController;

	@Test
	void contextLoads() {
		assertThat(recordController).isNotNull();
	}

}
