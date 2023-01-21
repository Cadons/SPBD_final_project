package ch.supsi.backend_api_rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employee", schema = "chinook", catalog = "")
public class EmployeeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "employeeid")
    private int employeeid;
    @Basic
    @Column(name = "lastname")
    private String lastname;
    @Basic
    @Column(name = "firstname")
    private String firstname;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "reportsto")
    private Integer reportsto;
    @Basic
    @Column(name = "birthdate")
    private Timestamp birthdate;
    @Basic
    @Column(name = "hiredate")
    private Timestamp hiredate;
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

    @Basic
    @Column(name = "isMenager")
    private boolean isMenager=false;

    @Basic
    @Column(name = "password")
    @JsonIgnore
    private String password;
    @Basic
    @Column(name = "username",unique = true)
    private String username;
    @OneToMany(mappedBy = "supportrepid")
    private List<CustomerEntity> customersByEmployeeid;

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReportsto() {
        return reportsto;
    }

    public void setReportsto(Integer reportsto) {
        this.reportsto = reportsto;
    }

    public Timestamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;
    }

    public Timestamp getHiredate() {
        return hiredate;
    }

    public void setHiredate(Timestamp hiredate) {
        this.hiredate = hiredate;
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

    public void setCustomersByEmployeeid(List<CustomerEntity> customersByEmployeeid) {
        this.customersByEmployeeid = customersByEmployeeid;
    }

    public List<CustomerEntity> getCustomersByEmployeeid() {
        return customersByEmployeeid;
    }

    public boolean isMenager() {
        return isMenager;
    }

    public void setMenager(boolean menager) {
        isMenager = menager;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return employeeid == that.employeeid && Objects.equals(lastname, that.lastname) && Objects.equals(firstname, that.firstname) && Objects.equals(title, that.title) && Objects.equals(reportsto, that.reportsto) && Objects.equals(birthdate, that.birthdate) && Objects.equals(hiredate, that.hiredate) && Objects.equals(address, that.address) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(country, that.country) && Objects.equals(postalcode, that.postalcode) && Objects.equals(phone, that.phone) && Objects.equals(fax, that.fax) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeid, lastname, firstname, title, reportsto, birthdate, hiredate, address, city, state, country, postalcode, phone, fax, email);
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }
}
