package be.hubertrm.dashboard.record.mapper;

import be.hubertrm.dashboard.record.dto.SourceDto;
import be.hubertrm.dashboard.record.model.Source;
import org.mapstruct.Mapper;

/**
 * The Interface SourceMapper provides the methods for mapping Source to SourceDto and back.
 */
@Mapper
public interface SourceMapper extends GenericMapper<Source, SourceDto> {
}
