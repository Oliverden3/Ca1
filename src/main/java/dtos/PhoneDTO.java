package dtos;

import entities.Person;
import entities.Phone;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PhoneDTO implements Serializable {
    private final int id;
    private final int number;
    private final String description;
    public PhoneDTO(int id, int number, String description) {
        this.id = id;
        this.number = number;
        this.description = description;
    }
    public PhoneDTO(Phone phone) {
        this.id = phone.getId();
        this.number = phone.getNumber();
        this.description = phone.getDescription();

    }
    public static List<PhoneDTO> getDTOs(List<Phone> phones) {
        List<PhoneDTO> phoneDTOList = new ArrayList<>();
        phones.forEach(phone -> {
            phoneDTOList.add(new PhoneDTO(phone));
        });
        return phoneDTOList;

    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "number = " + number + ", " +
                "description = " + description +
                ")";
    }
    public static class PersonInnerDTO implements Serializable {
        private final Integer id;

        private final String email;

        private final String firstName;
        private final String lastName;

        public PersonInnerDTO(Integer id, String email, String firstName, String lastName) {
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


        public Integer getId() {
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
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PersonInnerDTO that = (PersonInnerDTO) o;
            return id.equals(that.id) && email.equals(that.email) && firstName.equals(that.firstName) && lastName.equals(that.lastName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, email, firstName, lastName);
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

