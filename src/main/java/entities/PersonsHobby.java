package entities;

import javax.persistence.*;

@Entity
@Table(name = "persons_hobbies")
public class PersonsHobby {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private PersonsHobbyId id;

    @MapsId("hobbyId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "hobby_id", nullable = false)
    private Hobby hobby;

    @MapsId("personId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    public PersonsHobbyId getId() {
        return id;
    }

    public void setId(PersonsHobbyId id) {
        this.id = id;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "PersonsHobby{" +
                "id=" + id +
                ", hobby=" + hobby +
                ", person=" + person +
                '}';
    }
}