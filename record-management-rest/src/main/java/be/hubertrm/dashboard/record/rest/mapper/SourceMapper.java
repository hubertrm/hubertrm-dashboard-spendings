package be.hubertrm.dashboard.record.rest.mapper;

import be.hubertrm.dashboard.record.rest.dto.SourceDto;
import be.hubertrm.dashboard.record.rest.model.Source;
import org.mapstruct.Mapper;

/**
 * The Interface SourceMapper provides the methods for mapping Source to SourceDto and back.
 */
@Mapper
public interface SourceMapper extends GenericMapper<Source, SourceDto> {
}
