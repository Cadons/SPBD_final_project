package ch.supsi.backend_api_rest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "customer", schema = "chinook", catalog = "")
public class CustomerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "customerid")
    private int customerid;
    @Basic
    @Column(name = "firstname")
    private String firstname;
    @Basic
    @Column(name = "lastname")
    private String lastname;
    @Basic
    @Column(name = "company")
    private String company;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "city")
    private String city;
    @Basic
    @Column(name = "state")
    private String state;
    @Basic
    @Column(name = "country")
    private String country;
    @Basic
    @Column(name = "postalcode")
    private String postalcode;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "fax")
    private String fax;
    @Basic
    @Column(name = "email")
    private String email;
    @ManyToOne
    @JsonBackReference
    private EmployeeEntity supportrepid;

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public EmployeeEntity getSupportrepid() {
        return supportrepid;
    }

    public void setSupportrepid(EmployeeEntity supportrepid) {
        this.supportrepid = supportrepid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return customerid == that.customerid && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(company, that.company) && Objects.equals(address, that.address) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(country, that.country) && Objects.equals(postalcode, that.postalcode) && Objects.equals(phone, that.phone) && Objects.equals(fax, that.fax) && Objects.equals(email, that.email) && Objects.equals(supportrepid, that.supportrepid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerid, firstname, lastname, company, address, city, state, country, postalcode, phone, fax, email, supportrepid);
    }
}
