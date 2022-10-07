package dtos;

import entities.Address;
import entities.Cityinfo;
import entities.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CityInfoDTO implements Serializable {
    private final int id;
    private final int zipCode;
    private final String city;
    private List<AddressInnerDTO> addresses;
    public CityInfoDTO(int id, int zipCode, String city, List<AddressInnerDTO> addresses) {
        this.id = id;
        this.zipCode = zipCode;
        this.city = city;
        this.addresses = addresses;
    }
    public CityInfoDTO(Cityinfo cityinfo) {
        this.id = cityinfo.getId();
        this.zipCode = cityinfo.getZipCode();
        this.city = cityinfo.getCity();
    }
    public static List<CityInfoDTO> getDTOs(List<Cityinfo> cityinfos){
        List<CityInfoDTO> cityInfoDTOS = new ArrayList<>();
        cityinfos.forEach(cityinfo-> {
            cityInfoDTOS.add(new CityInfoDTO(cityinfo));
        });
        return cityInfoDTOS;

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

    public List<AddressInnerDTO> getAddresses() {
        return addresses;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "zipCode = " + zipCode + ", " +
                "city = " + city + ", " +
                "addresses = " + addresses + ")";
    }

    public static class AddressInnerDTO implements Serializable {
        private final int id;
        private final String address;
        private final String additionalInfo;
        public AddressInnerDTO(int id, String address, String additionalInfo) {
            this.id = id;
            this.address = address;
            this.additionalInfo = additionalInfo;
        }

        public AddressInnerDTO(Address address) {
            this.id = address.getId();
            this.address = address.getAddress();
            this.additionalInfo = address.getAdditionalInfo();
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

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "address = " + address + ", " +
                    "additionalInfo = " + additionalInfo + ")";
        }
    }
}
