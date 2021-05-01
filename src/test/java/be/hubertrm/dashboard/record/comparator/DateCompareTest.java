package be.hubertrm.dashboard.record.comparator;

import be.hubertrm.dashboard.record.dto.CategoryDto;
import be.hubertrm.dashboard.record.dto.RecordDto;
import be.hubertrm.dashboard.record.dto.SourceDto;
import be.hubertrm.dashboard.record.sample.SampleDataService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DateCompareTest {

    @Test
    void givenSameDate_whenComparing_returnZero() {
        LocalDate date = LocalDate.now();
        DateCompare comparator = new DateCompare();
        CategoryDto categoryDto = SampleDataService.createCategoryDto();
        categoryDto.setCreationDate(date);
        SourceDto sourceDto = SampleDataService.createSourceDto();
        sourceDto.setCreationDate(date);

        int result = comparator.compare(categoryDto, sourceDto);

        assertThat(result).isZero();
    }

    @Test
    void givenEarlierDate_whenComparing_returnNegative() {
        LocalDate date = LocalDate.now();
        DateCompare comparator = new DateCompare();
        SourceDto sourceDto = SampleDataService.createSourceDto();
        sourceDto.setCreationDate(date.minusDays(2));
        RecordDto recordDto = SampleDataService.createRecordDto();
        recordDto.setPayDate(date);

        int result = comparator.compare(sourceDto, recordDto);

        assertThat(result).isNegative();
    }

    @Test
    void givenLaterDate_whenComparing_returnPositive() {
        LocalDate date = LocalDate.now();
        DateCompare comparator = new DateCompare();
        CategoryDto categoryDto = SampleDataService.createCategoryDto();
        categoryDto.setCreationDate(date.plusDays(2));
        RecordDto recordDto = SampleDataService.createRecordDto();
        recordDto.setPayDate(date);

        int result = comparator.compare(categoryDto, recordDto);

        assertThat(result).isPositive();
    }
}