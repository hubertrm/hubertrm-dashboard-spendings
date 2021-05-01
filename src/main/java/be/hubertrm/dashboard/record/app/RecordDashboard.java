package be.hubertrm.dashboard.record.app;

import be.hubertrm.dashboard.record.comparator.DateCompare;
import be.hubertrm.dashboard.record.dto.CategoryDto;
import be.hubertrm.dashboard.record.dto.OrganisationDto;
import be.hubertrm.dashboard.record.dto.RecordDto;
import be.hubertrm.dashboard.record.dto.SourceDto;
import be.hubertrm.dashboard.record.manager.RecordManager;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Slf4j
public class RecordDashboard {

    public static void main(String[] args) {
        RecordManager recordManager = new RecordManager(Locale.FRANCE);
        CategoryDto categoryDto = recordManager.createCategory(1L, "Errand", LocalDate.now().minusMonths(1));
        OrganisationDto organisationDto = recordManager.createOrganisation(1L, "Belfius CO", "Brussels, 1000");
        SourceDto sourceDto = recordManager.createSource(1L, "Belfius", LocalDate.now().minusMonths(1), organisationDto.getId());
        RecordDto recordDto1 = recordManager.createRecord(1L, LocalDate.now(), categoryDto, sourceDto, -15.70F
                , "Errand Delhaize)");
        RecordDto recordDto2 = recordManager.createRecord(2L, LocalDate.now().plusDays(2), categoryDto, sourceDto, -100.00F
                , "Errand Cora)");
        RecordDto recordDto3 = recordManager.createRecord(3L, LocalDate.now().minusWeeks(2), categoryDto, sourceDto, -5.00F
                , "Errand Farm)");

        recordManager.printRecordReport();
        List<RecordDto> recordDtoList = Arrays.asList(recordDto1, recordDto2, recordDto3);
        log.info(recordDtoList.toString());
        DateCompare dateCompare = new DateCompare();
        recordDtoList.sort(dateCompare);
        log.info(recordDtoList.toString());
    }
}
