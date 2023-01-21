package ch.supsi.backend_api_rest.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "album", schema = "chinook", catalog = "")
public class AlbumEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "albumid")
    private int albumid;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "artistid")
    private int artistid;

    public int getAlbumid() {
        return albumid;
    }

    public void setAlbumid(int albumid) {
        this.albumid = albumid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArtistid() {
        return artistid;
    }

    public void setArtistid(int artistid) {
        this.artistid = artistid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumEntity that = (AlbumEntity) o;
        return albumid == that.albumid && artistid == that.artistid && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumid, title, artistid);
    }
}
