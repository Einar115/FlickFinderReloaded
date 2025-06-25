package mx.primis.content_service.model.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "albums")
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id", columnDefinition = "INT")
    private Long id;

    @Column(name = "album_name", nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private ArtistEntity artist;

    private Integer year;

    @Column(name = "album_spotify_id", length = 100)
    private String spotifyId;

    private String image;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private Set<TrackEntity> tracks = new HashSet<>();

    public AlbumEntity() {
    }

    public AlbumEntity(Long id, String name, ArtistEntity artist, Integer year, String spotifyId, String image, Set<TrackEntity> tracks) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.year = year;
        this.spotifyId = spotifyId;
        this.image = image;
        this.tracks = tracks;
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

    public ArtistEntity getArtist() {
        return artist;
    }

    public void setArtist(ArtistEntity artist) {
        this.artist = artist;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<TrackEntity> getTracks() {
        return tracks;
    }

    public void setTracks(Set<TrackEntity> tracks) {
        this.tracks = tracks;
    }
}