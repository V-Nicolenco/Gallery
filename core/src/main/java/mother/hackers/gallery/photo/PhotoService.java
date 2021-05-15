package mother.hackers.gallery.photo;

import mother.hackers.gallery.photo.dto.PhotoDto;
import mother.hackers.gallery.photo.dto.SavePhotoDto;

public interface PhotoService {

    PhotoDto savePhoto(SavePhotoDto dto);

    PhotoDto updateDescription(String description, long photoId, long userId);

    PhotoDto changeOpenStatus(boolean isOpen, long photoId, long userId);

    void deletePhoto(long photoId, long userId);
}
