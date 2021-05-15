package mother.hackers.gallery.album;

import mother.hackers.gallery.album.dto.AlbumDto;
import mother.hackers.gallery.album.dto.CreateAlbumDto;
import mother.hackers.gallery.exceptions.ForbiddenException;
import mother.hackers.gallery.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    private static final AlbumMapper mapper = AlbumMapper.INSTANCE;

    private final AlbumRepository albumRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public AlbumDto createAlbum(CreateAlbumDto dto) {
        Album newAlbum = mapper.toEntity(dto);
        Album album = albumRepository.save(newAlbum);

        return mapper.toDto(album);
    }

    @Override
    public AlbumDto getAlbum(long albumId, long userId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new NotFoundException("Album could not be found"));

        long ownerId = album.getOwner().getId();

        if (ownerId == userId || album.isPublic()) {
            return mapper.toDto(album);
        } else {
            throw new ForbiddenException("You do not have access to this album");
        }
    }

    @Override
    public List<AlbumDto> getMyAlbums(long userId) {
        return albumRepository.findAlbumsByUserId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlbumDto> getUserOpenAlbums(long userId) {
        return albumRepository.findOpenUserAlbumsByUserId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlbumDto> getOpenAlbums() {
        return albumRepository.findOpenAlbums().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByAlbumId(long albumId, long userId) {
        boolean isOwner = albumRepository.isOwner(albumId, userId);
        if (isOwner) {
            albumRepository.deleteById(albumId);
        }
        throw new ForbiddenException("You do not have access to delete this album");
    }
}
