package mx.primis.interaction_service.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorite_albums")
public class FavoriteAlbumsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorites_album_id", columnDefinition = "INT")
    private Long id;

    @Column(name = "user_id", columnDefinition = "INT")
    private Long userId;

    @Column(name = "album_id", columnDefinition = "INT")
    private Long albumId;

    @Column(name = "added_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime addedAt=LocalDateTime.now();

    public FavoriteAlbumsEntity() {
    }

    public FavoriteAlbumsEntity(Long id, Long userId, Long albumId, LocalDateTime addedAt) {
        this.id = id;
        this.userId = userId;
        this.albumId = albumId;
        this.addedAt = addedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}
