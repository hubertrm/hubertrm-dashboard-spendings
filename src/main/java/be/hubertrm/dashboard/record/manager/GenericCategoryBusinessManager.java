package be.hubertrm.dashboard.record.manager;

import be.hubertrm.dashboard.record.dto.CategoryDto;
import be.hubertrm.dashboard.record.mapper.CategoryMapper;
import be.hubertrm.dashboard.record.model.Category;
import be.hubertrm.dashboard.record.service.CategoryGenericService;
import org.mapstruct.factory.Mappers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class GenericCategoryBusinessManager extends BusinessManager<CategoryDto, Category> {

    public GenericCategoryBusinessManager(CategoryGenericService genericService, JpaRepository<Category, Long> jpa) {
        super(Mappers.getMapper(CategoryMapper.class), genericService, jpa);
    }
}
