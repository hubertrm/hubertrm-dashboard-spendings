package be.hubertrm.dashboard.record.rest.mapper;

import be.hubertrm.dashboard.record.rest.dto.CategoryDto;
import be.hubertrm.dashboard.record.rest.model.Category;
import org.mapstruct.Mapper;

/**
 * The Interface CategoryMapper provides the methods for mapping Category to CategoryDto and back.
 */
@Mapper
public interface CategoryMapper extends GenericMapper<Category, CategoryDto> {
}
