package ch.supsi.backend_api_rest.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "mediatype", schema = "chinook", catalog = "")
public class MediatypeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "mediatypeid")
    private int mediatypeid;
    @Basic
    @Column(name = "name")
    private String name;

    public int getMediatypeid() {
        return mediatypeid;
    }

    public void setMediatypeid(int mediatypeid) {
        this.mediatypeid = mediatypeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediatypeEntity that = (MediatypeEntity) o;
        return mediatypeid == that.mediatypeid && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediatypeid, name);
    }
}
