package be.hubertrm.dashboard.record.rest.controller;

import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.core.sample.SampleDataService;
import be.hubertrm.dashboard.record.files.enums.FileType;
import be.hubertrm.dashboard.record.files.exception.ReadWriteException;
import be.hubertrm.dashboard.record.files.exception.StorageException;
import be.hubertrm.dashboard.record.files.manager.ReadManager;
import be.hubertrm.dashboard.record.files.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(ReadFileController.class)
class ReadFileControllerMockMvcWithContextTest {

    private RecordDto recordDto;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StorageService storageService;
    @MockBean
    private ReadManager readManager;

    @Autowired
    private JacksonTester<List<RecordDto>> jsonRecordList;

    @BeforeEach
    void setup() {
        recordDto = SampleDataService.createRecordDto();
    }

    @Test
    void givenCsvFile_whenRequestToReadCsv_thenContentIsMappedAndReturned() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());
        given(readManager.readFile("test.txt", FileType.CSV)).willReturn(Collections.singletonList(recordDto));

        // when
        this.mvc.perform(multipart("/api/v1/read/csv").file(multipartFile))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().json(jsonRecordList.write(Collections.singletonList(recordDto)).getJson()));

        // then
        then(this.storageService).should().store(multipartFile);
    }

    @Test
    void givenCsvFile_whenStorageFail_thenReturnStorageException() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());
        doThrow(new StorageException("test")).when(storageService).store(multipartFile);

        // when
        this.mvc.perform(multipart("/api/v1/read/csv").file(multipartFile))
                .andExpect(status().is5xxServerError())
                .andExpect(mvcResult -> assertThat(mvcResult.getResolvedException()).isExactlyInstanceOf(StorageException.class))
                .andExpect(mvcResult -> assertThat(Objects.requireNonNull(mvcResult.getResolvedException()).getMessage()).isEqualTo("test"));
    }

    @Test
    void givenCsvFile_whenReadingFail_thenReturnException() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());
        doThrow(new ReadWriteException("test"))
                .when(readManager).readFile(multipartFile.getOriginalFilename(), FileType.CSV);

        // when
        this.mvc.perform(multipart("/api/v1/read/csv").file(multipartFile))
                .andExpect(status().is5xxServerError())
                .andExpect(mvcResult -> assertThat(mvcResult.getResolvedException()).isExactlyInstanceOf(ReadWriteException.class))
                .andExpect(mvcResult -> assertThat(Objects.requireNonNull(mvcResult.getResolvedException()).getMessage()).isEqualTo("test"));
    }

}