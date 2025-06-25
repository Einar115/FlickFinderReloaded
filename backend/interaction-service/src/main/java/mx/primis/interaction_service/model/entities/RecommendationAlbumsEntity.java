package mx.primis.interaction_service.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "recommendation_albums")
public class RecommendationAlbumsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id", columnDefinition = "INT")
    private Long id;

    @Column(name = "user_id", columnDefinition = "INT")
    private Long userId;

    @Column(name = "album_id", columnDefinition = "INT")
    private Long albumId;

    @Column(name = "relevance_score")
    private float relevanceScore;

    public RecommendationAlbumsEntity() {
    }

    public RecommendationAlbumsEntity(Long id, Long userId, Long albumId, float relevanceScore) {
        this.id = id;
        this.userId = userId;
        this.albumId = albumId;
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

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public float getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(float relevanceScore) {
        this.relevanceScore = relevanceScore;
    }
}
