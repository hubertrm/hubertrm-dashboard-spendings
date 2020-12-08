package be.hubertrm.dashboard.record.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private Long id;
    private String name;
    private Timestamp creationDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="ISEQ$$_73183")
    @SequenceGenerator(name="ISEQ$$_73183", sequenceName="ISEQ$$_73183", allocationSize=1)
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

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
