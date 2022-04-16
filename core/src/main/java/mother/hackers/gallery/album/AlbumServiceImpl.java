package mother.hackers.gallery.album;

import mother.hackers.gallery.album.dto.AlbumDto;
import mother.hackers.gallery.album.dto.CreateAlbumDto;
import mother.hackers.gallery.album.dto.EditAlbumDto;
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

    private final AlbumValidator albumValidator;

    public AlbumServiceImpl(AlbumRepository albumRepository,
                            UserRepository userRepository,
                            AlbumValidator albumValidator) {
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
        this.albumValidator = albumValidator;
    }

    @Override
    public AlbumDto createAlbum(CreateAlbumDto albumDto, long userId) {
        albumValidator.validateCreate(albumDto);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User could not be found"));

        Album newAlbum = mapper.toEntity(albumDto);
        newAlbum.setOwner(user);

        Album createdAlbum = albumRepository.save(newAlbum);
        return mapper.toDto(createdAlbum);
    }

    @Override
    public AlbumDto getAlbum(long albumId, long userId) {
        albumValidator.validateGetById(albumId, userId);

        return albumRepository.findById(albumId)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Album not found"));
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
    public AlbumDto editAlbum(long albumId, EditAlbumDto editAlbumDto, long userId) {
        albumValidator.validateEditAlbum(albumId, editAlbumDto, userId);

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new NotFoundException("Album not found"));
        album.setName(editAlbumDto.getName());
        album.setPublic(editAlbumDto.isPublic());

        Album editedAlbum = albumRepository.save(album);
        return mapper.toDto(editedAlbum);
    }

    @Override
    public void deleteByAlbumId(long albumId, long userId) {
        albumValidator.validateDeleteAlbum(albumId, userId);

        albumRepository.deleteById(albumId);
    }
}