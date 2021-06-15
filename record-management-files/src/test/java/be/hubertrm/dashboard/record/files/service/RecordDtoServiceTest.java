package be.hubertrm.dashboard.record.files.service;

import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.core.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.core.model.Category;
import be.hubertrm.dashboard.record.core.model.Source;
import be.hubertrm.dashboard.record.core.sample.SampleDataService;
import be.hubertrm.dashboard.record.core.service.CategoryService;
import be.hubertrm.dashboard.record.core.service.SourceService;
import be.hubertrm.dashboard.record.files.service.impl.RecordDtoServiceImpl;
import be.hubertrm.dashboard.record.files.spring.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class RecordDtoServiceTest extends TestConfig {

    @MockBean
    private CategoryService categoryService;
    @MockBean
    private SourceService sourceService;

    @InjectMocks
    @Autowired
    private RecordDtoServiceImpl recordDtoService;

    @BeforeEach
    void setup() throws ResourceNotFoundException {
        Category category = SampleDataService.createCategory();
        Source source = SampleDataService.createSource();
        given(categoryService.getCategoryByName("test")).willReturn(category);
        given(sourceService.getSourceByName("test")).willReturn(source);
    }

    @Test
    void shouldUseDefaultLocale() {
        String[] fields = {"date", "price", "category", "source", "note"};
        String testLine = "01-09-2021,\"1,0 €\",test,test,test";
        RecordDto expected = SampleDataService.createRecordDto()
                .setId(-1L)
                .setPayDate(LocalDate.of(2021, 9, 1));

        RecordDto actual = recordDtoService.create(fields, testLine);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldRespectSelectedLocale() {
        String[] fieldsFr = {"date", "montant", "catégorie", "compte", "commentaire"};
        String testLine = "01-09-2021,\"1,0 €\",test,test,test";
        RecordDto expected = SampleDataService.createRecordDto()
                .setId(-1L)
                .setPayDate(LocalDate.of(2021, 9, 1));

        RecordDto actual = recordDtoService.create(fieldsFr, testLine, Locale.FRANCE);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldMatchFieldsOrder() {
        String[] fields = {"note", "source", "category", "price", "date"};
        String testLine = "test,test,test,\"1,0 €\",01-09-2021";
        RecordDto expected = SampleDataService.createRecordDto()
                .setId(-1L)
                .setPayDate(LocalDate.of(2021, 9, 1));

        RecordDto actual = recordDtoService.create(fields, testLine);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldIgnoreUnsupportedFields() {
        String[] fields = {"date", "price", "unsupported_1", "category", "source", "note", "unsupported_2"};
        String testLine = "01-09-2021,\"1,0 €\",unsupported_1,test,test,test,unsupported_2";
        RecordDto expected = SampleDataService.createRecordDto()
                .setId(-1L)
                .setPayDate(LocalDate.of(2021, 9, 1));

        RecordDto actual = recordDtoService.create(fields, testLine);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenSelectedLocaleAndFieldsNameAreNotSupported_shouldReturnEmpty() {
        String[] fieldsIt = {"data", "importo", "categoria", "conto", "commento"};
        String testLine = "test,test,test,test,test";
        RecordDto expected = new RecordDto();

        RecordDto actual = recordDtoService.create(fieldsIt, testLine, Locale.ITALY);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}