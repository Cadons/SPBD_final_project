package ch.supsi.backend_api_rest.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "genre", schema = "chinook", catalog = "")
public class GenreEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "genreid")
    private int genreid;
    @Basic
    @Column(name = "name")
    private String name;

    public int getGenreid() {
        return genreid;
    }

    public void setGenreid(int genreid) {
        this.genreid = genreid;
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
        GenreEntity that = (GenreEntity) o;
        return genreid == that.genreid && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreid, name);
    }
}
