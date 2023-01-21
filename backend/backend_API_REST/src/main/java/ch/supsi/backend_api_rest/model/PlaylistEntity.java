package ch.supsi.backend_api_rest.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "playlist", schema = "chinook", catalog = "")
public class PlaylistEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "playlistid")
    private int playlistid;
    @Basic
    @Column(name = "name")
    private String name;

    public int getPlaylistid() {
        return playlistid;
    }

    public void setPlaylistid(int playlistid) {
        this.playlistid = playlistid;
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
        PlaylistEntity that = (PlaylistEntity) o;
        return playlistid == that.playlistid && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistid, name);
    }
}
