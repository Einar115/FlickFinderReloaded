package mx.primis.content_service.Services;

import mx.primis.content_service.model.dto.responses.AlbumResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface MusicService {
    List<AlbumResponse> getRecentAlbums(int count);
    AlbumResponse getAlbumDetails(Long id);
    List<AlbumResponse> getAlbumsByIds(List<Long> ids);
}
