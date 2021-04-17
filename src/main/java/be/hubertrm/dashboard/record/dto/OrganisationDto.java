package be.hubertrm.dashboard.record.dto;

import lombok.*;

import java.util.Comparator;
import java.util.Objects;

/**
 * {@code Organisation} class represents properties and behaviors of organisation objects
 * in the record management system.
 * <br>
 *      Each organisation is composed of an id, a name and an address
 * <br>
 * <p> ON: April 04, 2021</p>
 *
 * @version 1.0
 * @author hubertrm
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationDto implements Comparable<OrganisationDto>, Comparator<OrganisationDto> {

    private Long id;
    private String name;
    private String address;

    @Override
    public String toString() {
        return "Organisation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * @param obj the object to be compared with
     * @return true if of the same type and names are equal
     */
    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof OrganisationDto && this.name.equals(((OrganisationDto) obj).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }

    @Override
    public int compareTo(OrganisationDto organisationDto) {
        return this.name.compareTo(organisationDto.name);
    }

    @Override
    public int compare(OrganisationDto organisationDto1, OrganisationDto organisationDto2) {
        return organisationDto1.compareTo(organisationDto2);
    }
}
