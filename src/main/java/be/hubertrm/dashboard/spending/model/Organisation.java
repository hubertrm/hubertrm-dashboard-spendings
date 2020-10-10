package be.hubertrm.dashboard.spending.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "organisation")
@AllArgsConstructor
@NoArgsConstructor
public class Organisation {

    private Long id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="ISEQ$$_73177")
    @SequenceGenerator(name="ISEQ$$_73177", sequenceName="ISEQ$$_73177", allocationSize=1)
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

    @Override
    public String toString() {
        return "Organisation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
