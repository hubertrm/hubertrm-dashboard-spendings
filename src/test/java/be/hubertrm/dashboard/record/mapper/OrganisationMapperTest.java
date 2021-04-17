package be.hubertrm.dashboard.record.mapper;

import be.hubertrm.dashboard.record.dto.OrganisationDto;
import be.hubertrm.dashboard.record.model.Organisation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class OrganisationMapperTest {

    OrganisationMapper mapper = Mappers.getMapper(OrganisationMapper.class);

    @Test
    void nullToOrganisation() {
        Assertions.assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void nullToOrganisationDto() {
        Assertions.assertThat(mapper.toDto(null)).isNull();
    }

    @Test
    void emptyToOrganisation() {
        Organisation organisationExpected = new Organisation(null, null, null);
        Assertions.assertThat(mapper.toEntity(new OrganisationDto(null, null, null)))
                .isEqualToComparingFieldByField(organisationExpected);
    }

    @Test
    void emptyToOrganisationDto() {
        OrganisationDto organisationDtoExpected = new OrganisationDto(null, null, null);
        Assertions.assertThat(mapper.toDto(new Organisation()))
                .isEqualToComparingFieldByField(organisationDtoExpected);
    }

    @Test
    void organisationDtoToOrganisation() {
        OrganisationDto organisationDto = new OrganisationDto(1L, "test", "test");
        Organisation organisationExpected = new Organisation(1L, "test", "test");

        Assertions.assertThat(mapper.toEntity(organisationDto))
                .isEqualToComparingFieldByField(organisationExpected);
    }

    @Test
    void organisationToOrganisationDto() {
        Organisation organisation = new Organisation(1L, "test", "test");
        OrganisationDto organisationDtoExpected = new OrganisationDto(1L, "test", "test");

        Assertions.assertThat(mapper.toDto(organisation))
                .isEqualToComparingFieldByField(organisationDtoExpected);
    }

    @Test
    void nullToOrganisationList() {
        Assertions.assertThat(mapper.toEntityList(Collections.singletonList(null))).contains((Organisation) null);
    }

    @Test
    void nullToOrganisationDtoList() {
        Assertions.assertThat(mapper.toDtoList(Collections.singletonList(null))).contains((OrganisationDto) null);
    }

    @Test
    void emptyToOrganisationList() {
        Assertions.assertThat(mapper.toEntityList(new ArrayList<>())).isEmpty();
    }

    @Test
    void emptyToOrganisationDtoList() {
        Assertions.assertThat(mapper.toDtoList(new ArrayList<>())).isEmpty();
    }

    @Test
    void organisationDtoListToOrganisationList() {
        List<OrganisationDto> organisationDtoList = Arrays.asList(new OrganisationDto(1L, "test1", "test1"),
                new OrganisationDto(2L, "test2", "test2"));
        Organisation organisationExpected1 = new Organisation(1L, "test1", "test1");
        Organisation organisationExpected2 = new Organisation(2L, "test2", "test2");

        Assertions.assertThat(mapper.toEntityList(organisationDtoList))
                .usingElementComparatorOnFields("id", "name", "address")
                .containsExactlyElementsOf(Arrays.asList(organisationExpected1, organisationExpected2));
    }

    @Test
    void organisationListToOrganisationDtoList() {
        List<Organisation> organisationList = Arrays.asList(new Organisation(1L, "test1", "test1"),
                new Organisation(2L, "test2", "test2"));
        OrganisationDto organisationDtoExpected1 = new OrganisationDto(1L, "test1", "test1");
        OrganisationDto organisationDtoExpected2 = new OrganisationDto(2L, "test2", "test2");

        Assertions.assertThat(mapper.toDtoList(organisationList))
                .usingElementComparatorOnFields("id", "name", "address")
                .containsExactlyElementsOf(Arrays.asList(organisationDtoExpected1, organisationDtoExpected2));
    }
}