package be.hubertrm.dashboard.record.sample;

import be.hubertrm.dashboard.record.dto.OrganisationDto;
import be.hubertrm.dashboard.record.model.Category;
import be.hubertrm.dashboard.record.model.Organisation;
import be.hubertrm.dashboard.record.model.Record;
import be.hubertrm.dashboard.record.model.Source;

import java.time.LocalDate;

public class SampleDataService {

    public static Record createRecord() {
        return createRecord(1L, LocalDate.now(), 1F, 1L, 1L, "test");
    }

    public static  Record createRecord(long id, LocalDate payDate, float amount, long categoryId, long sourceId, String comments) {
        return new Record(id, payDate, amount, categoryId, sourceId, comments);
    }

    public static Category createCategory() {
        return createCategory(1L, "test", LocalDate.now());
    }

    public static Category createCategory(Long id, String name, LocalDate creationDate) {
        return new Category(id, name, creationDate);
    }

    public static Source createSource() {
        return createSource(1L, "test", LocalDate.now(), 1L);
    }

    public static Source createSource(Long id, String name, LocalDate creationDate, Long organisationId) {
        return new Source(id, name, creationDate, organisationId);
    }

    public static Organisation createOrganisation() {
        return createOrganisation(1L, "test", "test");
    }

    public static Organisation createOrganisation(Long id, String name, String address) {
        return new Organisation(id, name, address);
    }

    public static OrganisationDto createOrganisationDto() {
        return createOrganisationDto(1L, "test", "test");
    }

    public static OrganisationDto createOrganisationDto(Long id, String name, String address) {
        return new OrganisationDto(id, name, address);
    }
}
