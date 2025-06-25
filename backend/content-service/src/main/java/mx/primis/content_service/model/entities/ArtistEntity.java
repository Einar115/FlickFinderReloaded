package mx.primis.content_service.model.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "artists")
public class ArtistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id", columnDefinition = "INT")
    private Long id;

    @Column(name = "artist_name", nullable = false, length = 100)
    private String name;

    @Column(name = "artist_spotify_id", length = 100)
    private String spotifyId;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private Set<AlbumEntity> albums = new HashSet<>();

    public ArtistEntity() {
    }

    public ArtistEntity(Long id, String name, String spotifyId, Set<AlbumEntity> albums) {
        this.id = id;
        this.name = name;
        this.spotifyId = spotifyId;
        this.albums = albums;
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

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public Set<AlbumEntity> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<AlbumEntity> albums) {
        this.albums = albums;
    }
}
