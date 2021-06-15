package be.hubertrm.dashboard.record.files.service.impl;

import be.hubertrm.dashboard.record.files.exception.StorageException;
import be.hubertrm.dashboard.record.files.properties.StorageProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Source:https://github.com/spring-guides/gs-uploading-files
 */
class FileSystemStorageServiceTest {

    private final StorageProperties properties = new StorageProperties();
    private FileSystemStorageService service;

    @BeforeEach
    void init() {
        properties.setLocation("target/files/" + Math.abs(new Random().nextLong()));
        service = new FileSystemStorageService(properties);
        service.init();
    }

    @Test
    void saveAndLoad() {
        service.store(new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, World".getBytes()));
        assertThat(service.load("foo.txt")).exists();
    }

    @Test
    void saveRelativePathNotPermitted() {
        MockMultipartFile multipartFile = new MockMultipartFile("foo", "../foo.txt",
                MediaType.TEXT_PLAIN_VALUE, "Hello, World".getBytes());
        assertThatThrownBy(() -> service.store(multipartFile)).isInstanceOf(StorageException.class);
    }

    @Test
    void saveAbsolutePathNotPermitted() {
        MockMultipartFile multipartFile = new MockMultipartFile("foo", "/etc/passwd",
                MediaType.TEXT_PLAIN_VALUE, "Hello, World".getBytes());
        assertThatThrownBy(() -> service.store(multipartFile)).isInstanceOf(StorageException.class);
    }

    @Test
    void savePermitted() {
        assertDoesNotThrow(() -> service.store(new MockMultipartFile("foo", "bar/../foo.txt",
                MediaType.TEXT_PLAIN_VALUE, "Hello, World".getBytes())));
    }

}