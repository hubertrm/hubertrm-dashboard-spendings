package be.hubertrm.dashboard.record.core.mapper;

import be.hubertrm.dashboard.record.core.dto.SourceDto;
import be.hubertrm.dashboard.record.core.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.core.model.Source;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class SourceMapperTest {

    SourceMapper mapper = Mappers.getMapper(SourceMapper.class);

    @Test
    void nullToSource() {
        Assertions.assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void nullToSourceDto() throws ResourceNotFoundException {
        Assertions.assertThat(mapper.toDto(null)).isNull();
    }

    @Test
    void emptyToSource() {
        Source sourceExpected = new Source(null, null, null, null);
        Assertions.assertThat(mapper.toEntity(new SourceDto(null, null, null, null)))
                .isEqualToComparingFieldByField(sourceExpected);
    }

    @Test
    void emptyToSourceDto() throws ResourceNotFoundException {
        SourceDto sourceDtoExpected = new SourceDto(null, null, null, null);
        Assertions.assertThat(mapper.toDto(new Source()))
                .isEqualToComparingFieldByField(sourceDtoExpected);
    }

    @Test
    void sourceDtoToSource() {
        LocalDate dateTest = LocalDate.now();
        SourceDto SourceDto = new SourceDto(1L, "test", dateTest, 1L);
        Source sourceExpected = new Source(1L, "test", dateTest, 1L);

        Assertions.assertThat(mapper.toEntity(SourceDto))
                .isEqualToComparingFieldByField(sourceExpected);
    }

    @Test
    void sourceToSourceDto() throws ResourceNotFoundException {
        LocalDate dateTest = LocalDate.now();
        Source Source = new Source(1L, "test", dateTest, 1L);
        SourceDto sourceDtoExpected = new SourceDto(1L, "test", dateTest, 1L);

        Assertions.assertThat(mapper.toDto(Source))
                .isEqualToComparingFieldByField(sourceDtoExpected);
    }

    @Test
    void nullToSourceList() {
        Assertions.assertThat(mapper.toEntityList(Collections.singletonList(null))).contains((Source) null);
    }

    @Test
    void nullToSourceDtoList() {
        Assertions.assertThat(mapper.toDtoList(Collections.singletonList(null))).contains((SourceDto) null);
    }

    @Test
    void emptyToSourceList() {
        Assertions.assertThat(mapper.toEntityList(new ArrayList<>())).isEmpty();
    }

    @Test
    void emptyToSourceDtoList() {
        Assertions.assertThat(mapper.toDtoList(new ArrayList<>())).isEmpty();
    }

    @Test
    void sourceDtoListToSourceList() {
        LocalDate dateTest1 = LocalDate.now();
        LocalDate dateTest2 = LocalDate.now().minusDays(2);
        List<SourceDto> sourceDtoList = Arrays.asList(new SourceDto(1L, "test1", dateTest1, 1L),
                new SourceDto(2L, "test2", dateTest2, 1L));
        Source sourceExpected1 = new Source(1L, "test1", dateTest1, 1L);
        Source sourceExpected2 = new Source(2L, "test2", dateTest2, 1L);

        Assertions.assertThat(mapper.toEntityList(sourceDtoList))
                .usingElementComparatorOnFields("id", "name", "creationDate", "organisationId")
                .containsExactlyElementsOf(Arrays.asList(sourceExpected1, sourceExpected2));
    }

    @Test
    void sourceListToSourceDtoList() {
        LocalDate dateTest1 = LocalDate.now();
        LocalDate dateTest2 = LocalDate.now().minusDays(2);
        List<Source> sourceList = Arrays.asList(new Source(1L, "test1", dateTest1, 1L),
                new Source(2L, "test2", dateTest2, 1L));
        SourceDto sourceDtoExpected1 = new SourceDto(1L, "test1", dateTest1, 1L);
        SourceDto sourceDtoExpected2 = new SourceDto(2L, "test2", dateTest2, 1L);

        Assertions.assertThat(mapper.toDtoList(sourceList))
                .usingElementComparatorOnFields("id", "name", "creationDate", "organisationId")
                .containsExactlyElementsOf(Arrays.asList(sourceDtoExpected1, sourceDtoExpected2));
    }
}