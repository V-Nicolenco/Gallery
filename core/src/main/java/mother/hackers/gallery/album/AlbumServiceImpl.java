package mother.hackers.gallery.album;

import mother.hackers.gallery.album.dto.AlbumDto;
import mother.hackers.gallery.album.dto.CreateAlbumDto;
import mother.hackers.gallery.exceptions.ForbiddenException;
import mother.hackers.gallery.exceptions.NotFoundException;
import mother.hackers.gallery.user.User;
import mother.hackers.gallery.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    private static final AlbumMapper mapper = AlbumMapper.INSTANCE;

    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository, UserRepository userRepository) {
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AlbumDto createAlbum(CreateAlbumDto dto, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User could not be found"));
        Album newAlbum = mapper.toEntity(dto);
        newAlbum.setOwner(user);
        Album album = albumRepository.save(newAlbum);

        return mapper.toDto(album);
    }

    @Override
    public AlbumDto getAlbum(long albumId, long userId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new NotFoundException("Album not found"));

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
    public List<AlbumDto> getUserPublicAlbums(long userId) {
        return albumRepository.findOpenUserAlbumsByUserId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlbumDto> getPublicAlbums() {
        return albumRepository.findPublicAlbums().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByAlbumId(long albumId, long userId) {
        boolean isOwner = albumRepository.isOwner(albumId, userId);
        if (isOwner) {
            albumRepository.deleteById(albumId);
        } else {
            throw new ForbiddenException("You do not have access to delete this album");
        }
    }
}
