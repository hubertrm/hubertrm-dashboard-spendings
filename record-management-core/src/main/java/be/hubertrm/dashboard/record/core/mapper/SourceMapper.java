package be.hubertrm.dashboard.record.core.mapper;

import be.hubertrm.dashboard.record.core.dto.SourceDto;
import be.hubertrm.dashboard.record.core.model.Source;
import org.mapstruct.Mapper;

/**
 * The Interface SourceMapper provides the methods for mapping Source to SourceDto and back.
 */
@Mapper
public interface SourceMapper extends GenericMapper<Source, SourceDto> {
}
