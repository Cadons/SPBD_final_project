package ch.supsi.backend_api_rest.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "artist", schema = "chinook", catalog = "")
public class ArtistEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "artistid")
    private int artistid;
    @Basic
    @Column(name = "name")
    private String name;

    public int getArtistid() {
        return artistid;
    }

    public void setArtistid(int artistid) {
        this.artistid = artistid;
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
        ArtistEntity that = (ArtistEntity) o;
        return artistid == that.artistid && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistid, name);
    }
}
