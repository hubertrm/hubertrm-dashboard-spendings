package be.hubertrm.dashboard.record.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

/**
 * {@code Category} class represents properties and behaviors of category objects
 * in the record management system.
 * <br>
 *      Each category is composed of an id, a name and a creation date
 * <br>
 * <p> ON: April 04, 2021</p>
 *
 * @version 1.0
 * @author hubertrm
 */
@Data
@AllArgsConstructor
public class CategoryDto implements Comparable<CategoryDto>, Datable {

    private static final String DEFAULT_CATEGORY = "NO CATEGORY";

    private Long id;
    private String name;
    private LocalDate creationDate;

    public CategoryDto() {
        this.id = -1L;
        this.name = DEFAULT_CATEGORY;
        this.creationDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    /**
     * @param obj the object to be compared with
     * @return true if of the same type and names are equal
     */
    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof CategoryDto && this.name.equals(((CategoryDto) obj).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate);
    }

    @Override
    public int compareTo(CategoryDto categoryDto) {
        return this.name.compareTo(categoryDto.name);
    }

    @Override
    public LocalDate getDate() {
        return getCreationDate();
    }
}
