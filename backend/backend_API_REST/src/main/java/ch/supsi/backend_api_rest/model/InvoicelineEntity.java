package ch.supsi.backend_api_rest.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "invoiceline", schema = "chinook", catalog = "")
public class InvoicelineEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "invoicelineid")
    private int invoicelineid;
    @Basic
    @Column(name = "invoiceid")
    private int invoiceid;
    @Basic
    @Column(name = "trackid")
    private int trackid;
    @Basic
    @Column(name = "unitprice")
    private BigDecimal unitprice;
    @Basic
    @Column(name = "quantity")
    private int quantity;

    public int getInvoicelineid() {
        return invoicelineid;
    }

    public void setInvoicelineid(int invoicelineid) {
        this.invoicelineid = invoicelineid;
    }

    public int getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(int invoiceid) {
        this.invoiceid = invoiceid;
    }

    public int getTrackid() {
        return trackid;
    }

    public void setTrackid(int trackid) {
        this.trackid = trackid;
    }

    public BigDecimal getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoicelineEntity that = (InvoicelineEntity) o;
        return invoicelineid == that.invoicelineid && invoiceid == that.invoiceid && trackid == that.trackid && quantity == that.quantity && Objects.equals(unitprice, that.unitprice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoicelineid, invoiceid, trackid, unitprice, quantity);
    }
}
