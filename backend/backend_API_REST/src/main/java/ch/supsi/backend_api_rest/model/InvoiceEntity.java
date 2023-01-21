package ch.supsi.backend_api_rest.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "invoice", schema = "chinook", catalog = "")
public class InvoiceEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "invoiceid")
    private int invoiceid;
    @Basic
    @Column(name = "customerid")
    private int customerid;
    @Basic
    @Column(name = "invoicedate")
    private Timestamp invoicedate;
    @Basic
    @Column(name = "billingaddress")
    private String billingaddress;
    @Basic
    @Column(name = "billingcity")
    private String billingcity;
    @Basic
    @Column(name = "billingstate")
    private String billingstate;
    @Basic
    @Column(name = "billingcountry")
    private String billingcountry;
    @Basic
    @Column(name = "billingpostalcode")
    private String billingpostalcode;
    @Basic
    @Column(name = "total")
    private BigDecimal total;

    public int getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(int invoiceid) {
        this.invoiceid = invoiceid;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public Timestamp getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(Timestamp invoicedate) {
        this.invoicedate = invoicedate;
    }

    public String getBillingaddress() {
        return billingaddress;
    }

    public void setBillingaddress(String billingaddress) {
        this.billingaddress = billingaddress;
    }

    public String getBillingcity() {
        return billingcity;
    }

    public void setBillingcity(String billingcity) {
        this.billingcity = billingcity;
    }

    public String getBillingstate() {
        return billingstate;
    }

    public void setBillingstate(String billingstate) {
        this.billingstate = billingstate;
    }

    public String getBillingcountry() {
        return billingcountry;
    }

    public void setBillingcountry(String billingcountry) {
        this.billingcountry = billingcountry;
    }

    public String getBillingpostalcode() {
        return billingpostalcode;
    }

    public void setBillingpostalcode(String billingpostalcode) {
        this.billingpostalcode = billingpostalcode;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceEntity that = (InvoiceEntity) o;
        return invoiceid == that.invoiceid && customerid == that.customerid && Objects.equals(invoicedate, that.invoicedate) && Objects.equals(billingaddress, that.billingaddress) && Objects.equals(billingcity, that.billingcity) && Objects.equals(billingstate, that.billingstate) && Objects.equals(billingcountry, that.billingcountry) && Objects.equals(billingpostalcode, that.billingpostalcode) && Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceid, customerid, invoicedate, billingaddress, billingcity, billingstate, billingcountry, billingpostalcode, total);
    }
}
