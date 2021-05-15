package mother.hackers.gallery.photo;

import mother.hackers.gallery.exceptions.ForbiddenException;
import mother.hackers.gallery.exceptions.NotFoundException;
import mother.hackers.gallery.photo.dto.PhotoDto;
import mother.hackers.gallery.photo.dto.SavePhotoDto;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService {

    private static final PhotoMapper mapper = PhotoMapper.INSTANCE;

    private final PhotoRepository photoRepository;

    public PhotoServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public PhotoDto savePhoto(SavePhotoDto dto) {
        Photo newPhoto = mapper.toEntity(dto);
        Photo savedPhoto = photoRepository.save(newPhoto);

        return mapper.toDto(savedPhoto);
    }

    @Override
    public PhotoDto updateDescription(String description, long photoId, long userId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException("Photo could not be found"));

        long ownerId = photo.getOwner().getId();
        if (ownerId == userId) {
            photo.setDescription(description);
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
            photo.setOpen(isOpen);
            Photo updatedPhoto = photoRepository.save(photo);
            return mapper.toDto(updatedPhoto);
        } else {
            throw new ForbiddenException("Only the owner can change the status of this photo.");
        }
    }

    @Override
    public void deletePhoto(long photoId, long userId) {
        if (photoRepository.isOwner(photoId, userId)) {
            photoRepository.deleteById(photoId);
        } else {
            throw new ForbiddenException("Only the owner can delete this photo.");
        }
    }
}
