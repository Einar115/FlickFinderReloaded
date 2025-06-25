package mx.primis.content_service.Services.Impl;

import mx.primis.content_service.Services.MusicService;
import mx.primis.content_service.mappers.AlbumMapper;
import mx.primis.content_service.model.dto.responses.AlbumResponse;
import mx.primis.content_service.model.entities.AlbumEntity;
import mx.primis.content_service.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired private AlbumRepository albumRepository;

    @Override
    public List<AlbumResponse> getRecentAlbums(int count) {
        List<AlbumEntity> entities = albumRepository.findAll(
                PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "year"))
        ).getContent();

        return AlbumMapper.toResponseList(entities);
    }

    @Transactional(readOnly = true)
    @Override
    public AlbumResponse getAlbumDetails(Long id) {
        AlbumEntity album = albumRepository.findByIdWithTracks(id)
                .orElseThrow(() -> new RuntimeException("Album not found with id: " + id));
        return AlbumMapper.toResponse(album);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AlbumResponse> getAlbumsByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        List<AlbumEntity> entities = albumRepository.findAllByIdWithTracks(ids);
        if (entities.size() != ids.size()) {
            List<Long> foundIds = entities.stream()
                    .map(AlbumEntity::getId)
                    .toList();

            List<Long> missingIds = ids.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();
        }
        return AlbumMapper.toResponseList(entities);
    }
}
