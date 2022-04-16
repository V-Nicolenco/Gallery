package mother.hackers.gallery.album;

import mother.hackers.gallery.album.dto.AlbumDto;
import mother.hackers.gallery.album.dto.CreateAlbumDto;
import mother.hackers.gallery.album.dto.EditAlbumDto;

import java.util.List;

public interface AlbumService {

    AlbumDto createAlbum(CreateAlbumDto dto, long userId);

    AlbumDto getAlbum(long albumId, long userId);

    List<AlbumDto> getMyAlbums(long userId);

    List<AlbumDto> getUserPublicAlbums(long userId);

    List<AlbumDto> getPublicAlbums();

    AlbumDto editAlbum(long albumId, EditAlbumDto editAlbumDto, long userId);

    void deleteByAlbumId(long albumId, long userId);
}
