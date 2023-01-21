package ch.supsi.backend_api_rest.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class PlaylisttrackEntityPK implements Serializable {
    @Column(name = "playlistid")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int playlistid;
    @Column(name = "trackid")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        PlaylisttrackEntityPK that = (PlaylisttrackEntityPK) o;
        return playlistid == that.playlistid && trackid == that.trackid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistid, trackid);
    }
}
