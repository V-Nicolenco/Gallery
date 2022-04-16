package mother.hackers.gallery.photo;

import mother.hackers.gallery.photo.dto.PhotoDto;
import mother.hackers.gallery.photo.dto.UpdateDescriptionDto;

import java.util.List;

public interface PhotoService {

    PhotoDto savePhoto(ImageData data, long userId);

    PhotoDto getPhotoById(long photoId, long userId);

    List<PhotoDto> getAllByAlbumId(long albumId, long userId);

    PhotoDto updateDescription(UpdateDescriptionDto description, long photoId, long userId);

    PhotoDto changeOpenStatus(boolean isOpen, long photoId, long userId);

    void deletePhotoById(long photoId, long userId);
}
