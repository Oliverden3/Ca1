package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "Cityinfo.deleteAllRows", query = "DELETE from Cityinfo"),
        @NamedQuery(name = "Cityinfo.getAll", query = "SELECT c FROM Cityinfo c"),
})
@Table(name = "cityinfo")
public class Cityinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cityinfo_id", nullable = false)
    private int id;

    @Column(name = "zipCode", nullable = false)
    private int zipCode;

    @Column(name = "city", nullable = false, length = 45)
    private String city;

    @OneToMany(mappedBy = "cityinfo",cascade = CascadeType.PERSIST)
    private Set<Address> addresses = new LinkedHashSet<>();

    public int getId() {
        return id;
    }

    public Cityinfo() {
    }

    public Cityinfo(int zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public Cityinfo(int id, int zipCode, String city) {
        this.id = id;
        this.zipCode = zipCode;
        this.city = city;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cityinfo)) return false;
        Cityinfo cityinfo = (Cityinfo) o;
        return getId() == cityinfo.getId() && getZipCode() == cityinfo.getZipCode() && getCity().equals(cityinfo.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getZipCode(), getCity());
    }

    @Override
    public String toString() {
        return "Cityinfo{" +
                "id=" + id +
                ", zipCode=" + zipCode +
                ", city='" + city + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}