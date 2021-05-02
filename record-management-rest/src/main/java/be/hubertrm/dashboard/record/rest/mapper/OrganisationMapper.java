package be.hubertrm.dashboard.record.rest.mapper;

import be.hubertrm.dashboard.record.rest.dto.OrganisationDto;
import be.hubertrm.dashboard.record.rest.model.Organisation;
import org.mapstruct.Mapper;

/**
 * The Interface OrganisationMapper provides the methods for mapping Organisation to OrganisationDto and back.
 */
@Mapper
public interface OrganisationMapper extends GenericMapper<Organisation, OrganisationDto> {
}
