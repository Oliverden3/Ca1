package dtos;

import entities.Address;
import entities.Cityinfo;
import entities.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddressDTO implements Serializable {
    private final int id;
    private final String address;
    private final String additionalInfo;
    private final CityInfoInnerDTO cityinfo;
    private List<PersonInnerDTO> people = new ArrayList<>();

    public AddressDTO(int id, String address, String additionalInfo, CityInfoInnerDTO cityinfo, List<PersonInnerDTO> people) {
        this.id = id;
        this.address = address;
        this.additionalInfo = additionalInfo;
        this.cityinfo = cityinfo;
        this.people = people;
    }

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.address = address.getAddress();
        this.additionalInfo = address.getAdditionalInfo();
        this.cityinfo = new CityInfoInnerDTO(address.getCityinfo());
        address.getPeople().forEach(person -> {
            people.add(new PersonInnerDTO(person));
        });
    }

    public static List<AddressDTO> getDTOs(List<Address> addresses){
        List<AddressDTO> addressDTOList = new ArrayList<>();
        addresses.forEach(address -> {
            addressDTOList.add(new AddressDTO(address));
        });
        return addressDTOList;

    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public CityInfoInnerDTO getCityinfo() {
        return cityinfo;
    }

    public List<PersonInnerDTO> getPeople() {
        return people;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "address = " + address + ", " +
                "additionalInfo = " + additionalInfo + ", " +
                "cityinfo = " + cityinfo + ", " +
                "people = " + people + ")";
    }

    public static class CityInfoInnerDTO implements Serializable {
        private final int id;
        private final int zipCode;
        private final String city;

        public CityInfoInnerDTO(int id, int zipCode, String city) {
            this.id = id;
            this.zipCode = zipCode;
            this.city = city;
        }

        public CityInfoInnerDTO(Cityinfo cityinfo) {
            this.id = cityinfo.getId();
            this.zipCode = cityinfo.getZipCode();
            this.city = cityinfo.getCity();
        }

        public int getId() {
            return id;
        }

        public int getZipCode() {
            return zipCode;
        }

        public String getCity() {
            return city;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "zipCode = " + zipCode + ", " +
                    "city = " + city + ")";
        }
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
