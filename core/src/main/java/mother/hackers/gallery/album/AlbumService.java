package mother.hackers.gallery.album;

import mother.hackers.gallery.album.dto.AlbumDto;
import mother.hackers.gallery.album.dto.CreateAlbumDto;

import java.util.List;

public interface AlbumService {

    AlbumDto createAlbum(CreateAlbumDto dto);

    AlbumDto getAlbum(long albumId, long userId);

    List<AlbumDto> getMyAlbums(long userId);

    List<AlbumDto> getUserOpenAlbums(long userId);

    List<AlbumDto> getOpenAlbums();

    void deleteByAlbumId(long albumId, long userId);
}
