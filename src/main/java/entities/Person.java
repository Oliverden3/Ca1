package entities;

import dtos.PersonDTO;
import dtos.PhoneDTO;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person "),
        @NamedQuery(name = "Person.getAll", query = "SELECT p FROM Person p"),
})

@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private int id;

    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Column(name = "firstName", nullable = false, length = 45)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 45)
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER, optional = false,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id", nullable = true)
    private Address address;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "persons_hobbies",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id"))
    private Set<Hobby> hobbies = new LinkedHashSet<>();

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "person", cascade = CascadeType.PERSIST)
    private Set<Phone> phones = new LinkedHashSet<>();

    public Person(String email, String firstName, String lastName, Address address, Set<Hobby> hobbies, Set<Phone> phones) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.hobbies = hobbies;
        this.phones = phones;
    }

    public Person() {
    }
    public Person(int id, String email, String firstName, String lastName, Address address) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Person(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public Person(String email, String firstName, String lastName, Address address) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Person(PhoneDTO.PersonInnerDTO personDTO){
        this.id = personDTO.getId();
        this.email = personDTO.getEmail();
        this.firstName = personDTO.getFirstName();
        this.lastName = personDTO.getLastName();
    }
/*
   public Person(PersonDTO personDTO) {
       this.id = personDTO.getId();
       this.email = personDTO.getEmail();
       this.firstName = personDTO.getFirstName();
       this.lastName = personDTO.getLastName();
       this.address = new Address(personDTO.getAddress().getId(), personDTO.getAddress().getAddress(), personDTO.getAddress().getAdditionalInfo(),
               new Cityinfo(personDTO.getAddress().getCityInfo().getId(), personDTO.getAddress().getCityInfo().getZipCode(),
                       personDTO.getAddress().getCityInfo().getCity()));
   }

 */

   public Person (PersonDTO personDTO){
        this.id = personDTO.getId();
       this.email = personDTO.getEmail();
       this.firstName = personDTO.getFirstName();
       this.lastName = personDTO.getLastName();
   }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public void addPhone(Phone phone){
        phones.add(phone);
    }

    public void addHobby(Hobby hobby){
        hobbies.add(hobby);
    }
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", hobbies=" + hobbies +
                ", phones=" + phones +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getEmail().equals(person.getEmail()) && getFirstName().equals(person.getFirstName()) && getLastName().equals(person.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getFirstName(), getLastName());
    }
}