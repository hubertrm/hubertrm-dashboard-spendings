package be.hubertrm.dashboard.record.mapper;

import be.hubertrm.dashboard.record.dto.CategoryDto;
import be.hubertrm.dashboard.record.model.Category;
import org.mapstruct.Mapper;

/**
 * The Interface CategoryMapper provides the methods for mapping Category to CategoryDto and back.
 */
@Mapper
public interface CategoryMapper extends GenericMapper<Category, CategoryDto> {
}
