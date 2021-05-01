package be.hubertrm.dashboard.record.dto;

import be.hubertrm.dashboard.record.comparator.Datable;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

/**
 * {@code Source} class represents properties and behaviors of source objects
 * in the record management system.
 * <br>
 *      Each source is composed of an id, a name, a creationDate and an organisation
 * <br>
 * <p> ON: April 04, 2021</p>
 *
 * @version 1.0
 * @author hubertrm
 */
@Data
@AllArgsConstructor
public class SourceDto implements Comparable<SourceDto>, Datable {

    private static final String DEFAULT_SOURCE = "NO SOURCE";

    private Long id;
    private String name;
    private LocalDate creationDate;
    private Long organisationId;

    public SourceDto() {
        this.id = -1L;
        this.name = DEFAULT_SOURCE;
        this.creationDate = LocalDate.now();
        this.organisationId = -1L;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", organisation=" + organisationId +
                '}';
    }

    /**
     * @param obj the object to be compared with
     * @return true if of the same type and names and organisations are equal
     */
    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof SourceDto
                && this.name.equals(((SourceDto) obj).name)
                && this.organisationId.equals(((SourceDto) obj).organisationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate, organisationId);
    }

    @Override
    public int compareTo(SourceDto sourceDto) {
        return this.name.compareTo(sourceDto.name);
    }

    @Override
    public LocalDate getDate() {
        return getCreationDate();
    }
}
