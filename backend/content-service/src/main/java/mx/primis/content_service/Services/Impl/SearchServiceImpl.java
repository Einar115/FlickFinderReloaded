package mx.primis.content_service.Services.Impl;

import mx.primis.content_service.Services.SearchService;
import mx.primis.content_service.mappers.AlbumMapper;
import mx.primis.content_service.mappers.MovieMapper;
import mx.primis.content_service.mappers.TrackMapper;
import mx.primis.content_service.model.dto.responses.AlbumResponse;
import mx.primis.content_service.model.dto.responses.MovieResponse;
import mx.primis.content_service.model.dto.responses.TrackResponse;
import mx.primis.content_service.model.entities.AlbumEntity;
import mx.primis.content_service.model.entities.MovieEntity;
import mx.primis.content_service.model.entities.TrackEntity;
import mx.primis.content_service.repository.AlbumRepository;
import mx.primis.content_service.repository.MovieRepository;
import mx.primis.content_service.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired private MovieRepository movieRepository;
    @Autowired private AlbumRepository albumRepository;
    @Autowired private TrackRepository trackRepository;

    @Override
    public List<MovieResponse> searchMovies(String query, int limit) {
        List<MovieEntity> entities = movieRepository.findByTitleContainingIgnoreCase(
                query, PageRequest.of(0, limit)
        );
        return MovieMapper.toResponseList(entities);
    }

    @Override
    public List<AlbumResponse> searchAlbums(String query, int limit) {
        List<AlbumEntity> entities = albumRepository.findByNameContainingIgnoreCase(
                query, PageRequest.of(0, limit)
        );
        return AlbumMapper.toResponseList(entities);
    }

    @Override
    public List<TrackResponse> searchTracks(String query, int limit) {
        List<TrackEntity> entities = trackRepository.findByNameContainingIgnoreCase(
                query, PageRequest.of(0, limit)
        );
        return TrackMapper.toResponseList(entities);
    }
}
