package ch.supsi.backend_api_rest.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "playlisttrack", schema = "chinook", catalog = "")
@IdClass(PlaylisttrackEntityPK.class)
public class PlaylisttrackEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "playlistid")
    private int playlistid;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "trackid")
    private int trackid;

    public int getPlaylistid() {
        return playlistid;
    }

    public void setPlaylistid(int playlistid) {
        this.playlistid = playlistid;
    }

    public int getTrackid() {
        return trackid;
    }

    public void setTrackid(int trackid) {
        this.trackid = trackid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaylisttrackEntity that = (PlaylisttrackEntity) o;
        return playlistid == that.playlistid && trackid == that.trackid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistid, trackid);
    }
}
