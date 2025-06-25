package mx.primis.interaction_service.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorite_movies")
public class FavoriteMoviesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorites_movies_id", columnDefinition = "INT")
    private Long id;

    @Column(name = "user_id", columnDefinition = "INT")
    private Long userId;

    @Column(name = "movie_id", columnDefinition = "INT")
    private Long movieId;

    @Column(name = "added_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime addedAt=LocalDateTime.now();

    public FavoriteMoviesEntity() {
    }

    public FavoriteMoviesEntity(Long id, Long userId, Long movieId, LocalDateTime addedAt) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
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

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}
