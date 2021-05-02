package be.hubertrm.dashboard.record.rest.sample;

import be.hubertrm.dashboard.record.rest.dto.CategoryDto;
import be.hubertrm.dashboard.record.rest.dto.OrganisationDto;
import be.hubertrm.dashboard.record.rest.dto.RecordDto;
import be.hubertrm.dashboard.record.rest.dto.SourceDto;
import be.hubertrm.dashboard.record.rest.model.Category;
import be.hubertrm.dashboard.record.rest.model.Organisation;
import be.hubertrm.dashboard.record.rest.model.Record;
import be.hubertrm.dashboard.record.rest.model.Source;

import java.time.LocalDate;

public class SampleDataService {

    private SampleDataService() {}

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

    public static CategoryDto createCategoryDto() {
        return createCategoryDto(1L);
    }

    public static CategoryDto createCategoryDto(Long id) {
        return createCategoryDto(id, "test", LocalDate.now());
    }

    public static CategoryDto createCategoryDto(Long id, String name, LocalDate creationDate) {
        return new CategoryDto(id, name, creationDate);
    }

    public static OrganisationDto createOrganisationDto() {
        return createOrganisationDto(1L, "test", "test");
    }

    public static OrganisationDto createOrganisationDto(Long id, String name, String address) {
        return new OrganisationDto(id, name, address);
    }

    public static RecordDto createRecordDto() {
        return createRecordDto(1L, LocalDate.now(), 1F, createCategoryDto(), createSourceDto(), "test");
    }

    public static RecordDto createRecordDto(Long id, LocalDate payDate, Float amount, CategoryDto categoryDto, SourceDto sourceDto, String comments) {
        return new RecordDto(id, payDate, amount, categoryDto, sourceDto, comments);
    }

    public static SourceDto createSourceDto() {
        return createSourceDto(1L);
    }

    public static SourceDto createSourceDto(Long id) {
        return createSourceDto(id, "test", LocalDate.now(), 1L);
    }

    public static SourceDto createSourceDto(Long id, String name, LocalDate creationDate, Long organisationId) {
        return new SourceDto(id, name, creationDate, organisationId);
    }
}
