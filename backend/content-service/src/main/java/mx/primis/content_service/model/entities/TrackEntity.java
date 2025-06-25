package mx.primis.content_service.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tracks")
public class TrackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id", columnDefinition = "INT")
    private Long id;

    @Column(name = "track_name", nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private AlbumEntity album;

    @Column(name = "track_number")
    private Integer trackNumber;

    @Column(name = "disc_number")
    private Integer discNumber;

    private Boolean explicit;

    @Column(name = "duration_ms")
    private Integer durationMs;

    @Column(name = "track_spotify_id", length = 100)
    private String spotifyId;

    public TrackEntity() {
    }

    public TrackEntity(Long id, String name, AlbumEntity album, Integer trackNumber, Integer discNumber, Boolean explicit, Integer durationMs, String spotifyId) {
        this.id = id;
        this.name = name;
        this.album = album;
        this.trackNumber = trackNumber;
        this.discNumber = discNumber;
        this.explicit = explicit;
        this.durationMs = durationMs;
        this.spotifyId = spotifyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AlbumEntity getAlbum() {
        return album;
    }

    public void setAlbum(AlbumEntity album) {
        this.album = album;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public Integer getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(Integer discNumber) {
        this.discNumber = discNumber;
    }

    public Boolean getExplicit() {
        return explicit;
    }

    public void setExplicit(Boolean explicit) {
        this.explicit = explicit;
    }

    public Integer getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(Integer durationMs) {
        this.durationMs = durationMs;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }
}
