package mx.primis.interaction_service.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "recommendation_movies")
public class RecommendationMoviesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id", columnDefinition = "INT")
    private Long id;

    @Column(name = "user_id", columnDefinition = "INT")
    private Long userId;

    @Column(name = "movie_id", columnDefinition = "INT")
    private Long movieId;

    @Column(name = "relevance_score")
    private float relevanceScore;

    public RecommendationMoviesEntity() {
    }

    public RecommendationMoviesEntity(Long id, Long userId, Long movieId, float relevanceScore) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
        this.relevanceScore = relevanceScore;
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

    public float getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(float relevanceScore) {
        this.relevanceScore = relevanceScore;
    }
}
