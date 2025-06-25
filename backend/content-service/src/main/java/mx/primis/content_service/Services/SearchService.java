package mx.primis.content_service.Services;

import mx.primis.content_service.model.dto.responses.AlbumResponse;
import mx.primis.content_service.model.dto.responses.MovieResponse;
import mx.primis.content_service.model.dto.responses.TrackResponse;

import java.util.List;

public interface SearchService {
    List<MovieResponse> searchMovies(String query, int limit);
    List<AlbumResponse> searchAlbums(String query, int limit);
    List<TrackResponse> searchTracks(String query, int limit);
}
