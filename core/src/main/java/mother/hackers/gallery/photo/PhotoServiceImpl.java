package mother.hackers.gallery.photo;

import mother.hackers.gallery.exceptions.ForbiddenException;
import mother.hackers.gallery.exceptions.NotFoundException;
import mother.hackers.gallery.photo.dto.PhotoDto;
import mother.hackers.gallery.photo.dto.SavePhotoDto;
import mother.hackers.gallery.photo.dto.UpdateDescriptionDto;
import mother.hackers.gallery.user.User;
import mother.hackers.gallery.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService {

    private static final PhotoMapper mapper = PhotoMapper.INSTANCE;

    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    public PhotoServiceImpl(PhotoRepository photoRepository, UserRepository userRepository) {
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PhotoDto savePhoto(SavePhotoDto dto, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User could not be found"));

        Photo newPhoto = mapper.toEntity(dto);
        newPhoto.setOwner(user);
        Photo savedPhoto = photoRepository.save(newPhoto);

        return mapper.toDto(savedPhoto);
    }

    @Override
    public PhotoDto getPhotoById(long photoId, long userId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException("Photo could not be found"));

        long ownerId = photo.getOwner().getId();
        if (photo.isPublic() || ownerId == userId) {
            return mapper.toDto(photo);
        } else {
            throw new ForbiddenException("This photo is private and only owner can see this photo");
        }
    }

    @Override
    public PhotoDto updateDescription(UpdateDescriptionDto description, long photoId, long userId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException("Photo could not be found"));

        long ownerId = photo.getOwner().getId();
        if (ownerId == userId) {
            photo.setDescription(description.getText());
            Photo updatedPhoto = photoRepository.save(photo);
            return mapper.toDto(updatedPhoto);
        } else {
            throw new ForbiddenException("Only the owner can change the description of this photo.");
        }
    }

    @Override
    public PhotoDto changeOpenStatus(boolean isOpen, long photoId, long userId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException("Photo could not be found"));

        long ownerId = photo.getOwner().getId();
        if (ownerId == userId) {
            photo.setPublic(isOpen);
            Photo updatedPhoto = photoRepository.save(photo);
            return mapper.toDto(updatedPhoto);
        } else {
            throw new ForbiddenException("Only the owner can change the status of this photo.");
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
