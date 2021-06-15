package be.hubertrm.dashboard.record.files;

import be.hubertrm.dashboard.record.files.properties.StorageProperties;
import be.hubertrm.dashboard.record.files.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "be.hubertrm.dashboard.record")
@EnableConfigurationProperties(StorageProperties.class)
public class RecordFilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecordFilesApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return args -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
