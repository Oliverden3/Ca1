package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "Hobby.deleteAllRows", query = "DELETE from Hobby"),
        @NamedQuery(name = "Hobby.getAll", query = "SELECT h FROM Hobby h"),
})
@Table(name = "hobby")
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_id", nullable = false)
    private int id;

    @Column(name = "description", nullable = false, length = 45)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "persons_hobbies",
            joinColumns = @JoinColumn(name = "hobby_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<Person> people = new LinkedHashSet<>();
    public Hobby(){}

    public Hobby(String description) {
        this.description = description;
    }

    public Hobby(String description, Set<Person> people) {
        this.description = description;
        this.people = people;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hobby hobby = (Hobby) o;
        return id == hobby.id && Objects.equals(description, hobby.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }



    @Override
    public String toString() {
        return "Hobby{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", people=" + people +
                '}';
    }
}