package dtos;

import entities.Hobby;
import entities.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HobbyDTO implements Serializable {
    private final int id;
    private final String description;
    private List<PersonInnerDTO> people = new ArrayList<>();
    public HobbyDTO(int id, String description, List<PersonInnerDTO> people) {
        this.id = id;
        this.description = description;
        this.people = people;
    }

    public HobbyDTO(Hobby hobby) {
        this.id = hobby.getId();
        this.description = hobby.getDescription();
        hobby.getPeople().forEach(person -> {
            people.add(new PersonInnerDTO(person));
        });
    }

    public static List<HobbyDTO> getDTOs(List<Hobby> hobbies) {
        List<HobbyDTO> hobbyDTOList = new ArrayList<>();
        hobbies.forEach(hobby -> {
            hobbyDTOList.add(new HobbyDTO(hobby));
        });
        return hobbyDTOList;

    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public List<PersonInnerDTO> getPeople() {
        return people;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "description = " + description + ", " +
                "people = " + people + ")";
    }

    public static class PersonInnerDTO implements Serializable {
        private final int id;
        private final String email;
        private final String firstName;
        private final String lastName;
        public PersonInnerDTO(int id, String email, String firstName, String lastName) {
            this.id = id;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
        }
        public PersonInnerDTO(Person person) {
            this.id = person.getId();
            this.email = person.getEmail();
            this.firstName = person.getFirstName();
            this.lastName = person.getLastName();
        }

        public int getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "email = " + email + ", " +
                    "firstName = " + firstName + ", " +
                    "lastName = " + lastName + ")";
        }
    }
}
