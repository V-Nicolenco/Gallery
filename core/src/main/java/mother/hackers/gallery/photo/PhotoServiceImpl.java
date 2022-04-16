package mother.hackers.gallery.photo;

import mother.hackers.gallery.album.AlbumRepository;
import mother.hackers.gallery.exceptions.ForbiddenException;
import mother.hackers.gallery.exceptions.NotFoundException;
import mother.hackers.gallery.photo.dto.PhotoDto;
import mother.hackers.gallery.photo.dto.PhotoDescriptionDto;
import mother.hackers.gallery.user.User;
import mother.hackers.gallery.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoServiceImpl implements PhotoService {

    private static final PhotoMapper mapper = PhotoMapper.INSTANCE;

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    public PhotoServiceImpl(AlbumRepository albumRepository, PhotoRepository photoRepository, UserRepository userRepository) {
        this.albumRepository = albumRepository;
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PhotoDto savePhoto(PhotoDescriptionDto descriptionDto, ImageData data, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User could not be found"));

        Photo newPhoto = new Photo();
        newPhoto.setData(data);
        newPhoto.setAuthor(user);
        newPhoto.setDescription(descriptionDto.getText());
        Photo savedPhoto = photoRepository.save(newPhoto);

        return mapper.toDto(savedPhoto);
    }

    @Override
    public PhotoDto getPhotoById(long photoId, long userId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException("Photo could not be found"));

        long ownerId = photo.getAuthor().getId();
        if (photo.isCommentsClosed() || ownerId == userId) {
            return mapper.toDto(photo);
        } else {
            throw new ForbiddenException("This photo is private and only owner can see this photo");
        }
    }

    @Override
    public List<PhotoDto> getAllByAlbumId(long albumId, long userId) {
        if (albumRepository.existsById(albumId) && albumRepository.isPublic(albumId)) {
            return photoRepository.findAllPhotosByAlbumId(albumId)
                    .stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());
        } else {
            throw new ForbiddenException("This album is private and only owner can see photos in it");
        }
    }

    @Override
    public PhotoDto updateDescription(PhotoDescriptionDto description, long photoId, long userId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException("Photo could not be found"));

        long ownerId = photo.getAuthor().getId();
        if (ownerId == userId) {
            photo.setDescription(description.getText());
            Photo updatedPhoto = photoRepository.save(photo);
            return mapper.toDto(updatedPhoto);
        } else {
            throw new ForbiddenException("Only the owner can change the description of this photo.");
        }
    }

    @Override
    public PhotoDto changeOpenStatus(boolean commentsClosed, long photoId, long userId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException("Photo could not be found"));

        long authorId = photo.getAuthor().getId();
        if (authorId == userId) {
            photo.setCommentsClosed(commentsClosed);
            Photo updatedPhoto = photoRepository.save(photo);
            return mapper.toDto(updatedPhoto);
        } else {
            throw new ForbiddenException("Only the author can change the status of this photo.");
        }
    }

    @Override
    public void deletePhotoById(long photoId, long userId) {
        if (photoRepository.isOwner(photoId, userId)) {
            photoRepository.deleteById(photoId);
        } else {
            throw new ForbiddenException("Only the owner can delete this photo.");
        }
    }
}
