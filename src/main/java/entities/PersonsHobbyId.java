package entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PersonsHobbyId implements Serializable {
    private static final long serialVersionUID = -7880652430588998688L;
    @Column(name = "hobby_id", nullable = false)
    private int hobbyId;

    @Column(name = "person_id", nullable = false)
    private int personId;

    public int getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(int hobbyId) {
        this.hobbyId = hobbyId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "PersonsHobbyId{" +
                "hobbyId=" + hobbyId +
                ", personId=" + personId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonsHobbyId entity = (PersonsHobbyId) o;
        return Objects.equals(this.hobbyId, entity.hobbyId) &&
                Objects.equals(this.personId, entity.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hobbyId, personId);
    }

}