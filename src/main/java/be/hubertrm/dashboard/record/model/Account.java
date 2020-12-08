package be.hubertrm.dashboard.record.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Long id;
    private String name;
    private Timestamp creationDate;
    private Long organisationId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="ISEQ$$_73190")
    @SequenceGenerator(name="ISEQ$$_73190", sequenceName="ISEQ$$_73190", allocationSize=1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "creation_date", nullable = false)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "organisation_id", nullable = false)
    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", organisationId=" + organisationId +
                '}';
    }
}
