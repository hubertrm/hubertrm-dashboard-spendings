package be.hubertrm.dashboard.record.mapper;

import be.hubertrm.dashboard.record.dto.OrganisationDto;
import be.hubertrm.dashboard.record.model.Organisation;
import org.mapstruct.Mapper;

/**
 * The Interface OrganisationMapper provides the methods for mapping Organisation to OrganisationDto and back.
 */
@Mapper
public interface OrganisationMapper extends GenericMapper<Organisation, OrganisationDto> {
}
