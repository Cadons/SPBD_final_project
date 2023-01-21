package ch.supsi.backend_api_rest.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "track", schema = "chinook", catalog = "")
public class TrackEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "trackid")
    private int trackid;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "albumid")
    private Integer albumid;
    @Basic
    @Column(name = "mediatypeid")
    private int mediatypeid;
    @Basic
    @Column(name = "genreid")
    private Integer genreid;
    @Basic
    @Column(name = "composer")
    private String composer;
    @Basic
    @Column(name = "milliseconds")
    private int milliseconds;
    @Basic
    @Column(name = "bytes")
    private Integer bytes;
    @Basic
    @Column(name = "unitprice")
    private BigDecimal unitprice;

    public int getTrackid() {
        return trackid;
    }

    public void setTrackid(int trackid) {
        this.trackid = trackid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAlbumid() {
        return albumid;
    }

    public void setAlbumid(Integer albumid) {
        this.albumid = albumid;
    }

    public int getMediatypeid() {
        return mediatypeid;
    }

    public void setMediatypeid(int mediatypeid) {
        this.mediatypeid = mediatypeid;
    }

    public Integer getGenreid() {
        return genreid;
    }

    public void setGenreid(Integer genreid) {
        this.genreid = genreid;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    public Integer getBytes() {
        return bytes;
    }

    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }

    public BigDecimal getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackEntity that = (TrackEntity) o;
        return trackid == that.trackid && mediatypeid == that.mediatypeid && milliseconds == that.milliseconds && Objects.equals(name, that.name) && Objects.equals(albumid, that.albumid) && Objects.equals(genreid, that.genreid) && Objects.equals(composer, that.composer) && Objects.equals(bytes, that.bytes) && Objects.equals(unitprice, that.unitprice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackid, name, albumid, mediatypeid, genreid, composer, milliseconds, bytes, unitprice);
    }
}
