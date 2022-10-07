package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Phone.deleteAllRows", query = "DELETE from Phone "),
        @NamedQuery(name = "Phone.getAll", query = "SELECT p FROM Phone p"),
})
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id", nullable = false)
    private int id;

    @Column(name = "number", nullable = false, unique = true)
    private int number;

    @Column(name = "description", nullable = false, length = 45)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "person_id", nullable = true)
    private Person person;

    public Phone() {
    }

    public Phone(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public Phone(int number, String description, Person person) {
        this.number = number;
        this.description = description;
        this.person = person;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number=" + number +
                ", description='" + description + '\'' +
                ", person=" + person +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone)) return false;
        Phone phone = (Phone) o;
        return getId() == phone.getId() && getNumber() == phone.getNumber() && Objects.equals(getDescription(), phone.getDescription()) && Objects.equals(getPerson(), phone.getPerson());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getDescription(), getPerson());
    }
}