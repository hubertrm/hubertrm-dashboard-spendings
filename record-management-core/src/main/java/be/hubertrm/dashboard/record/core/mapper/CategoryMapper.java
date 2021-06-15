package be.hubertrm.dashboard.record.core.mapper;

import be.hubertrm.dashboard.record.core.dto.CategoryDto;
import be.hubertrm.dashboard.record.core.model.Category;
import org.mapstruct.Mapper;

/**
 * The Interface CategoryMapper provides the methods for mapping Category to CategoryDto and back.
 */
@Mapper
public interface CategoryMapper extends GenericMapper<Category, CategoryDto> {
}
