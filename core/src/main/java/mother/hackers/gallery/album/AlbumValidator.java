package mother.hackers.gallery.album;

import mother.hackers.gallery.album.dto.CreateAlbumDto;
import mother.hackers.gallery.album.dto.EditAlbumDto;
import mother.hackers.gallery.exceptions.ForbiddenException;
import mother.hackers.gallery.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AlbumValidator {

    private final AlbumRepository albumRepository;

    AlbumValidator(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    void validateCreate(CreateAlbumDto albumDto) {
        if (albumDto.getName().isBlank()) throw new IllegalArgumentException("Cannot create album with empty name");
    }

    void validateGetById(long albumId, long userId) {
        if (!albumRepository.existsById(albumId)) throw new NotFoundException("Album not found");
        if (!albumRepository.isPublic(albumId) || !albumRepository.isOwner(albumId, userId)) throw new ForbiddenException("You do not have access to this album");
    }

    void validateEditAlbum(long albumId, EditAlbumDto albumDto, long userId) {
        if (!albumRepository.existsById(albumId)) throw new NotFoundException("Album not found");
        if (!albumRepository.isOwner(albumId, userId)) throw new ForbiddenException("You do not have access to edit this album");

        if (albumDto.getName().isBlank()) throw new IllegalArgumentException("Cannot set empty name to album");
    }

    void validateDeleteAlbum(long albumId, long userId) {
        if (!albumRepository.existsById(albumId)) throw new NotFoundException("Album not found");
        if (!albumRepository.isOwner(albumId, userId)) throw new ForbiddenException("You do not have access to delete this album");
    }
}
