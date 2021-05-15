package mother.hackers.gallery.controllers;

import mother.hackers.gallery.photo.PhotoService;
import mother.hackers.gallery.photo.dto.ImageData;
import mother.hackers.gallery.photo.dto.PhotoDto;
import mother.hackers.gallery.photo.dto.UpdateDescriptionDto;
import mother.hackers.gallery.security.AuthenticationUser;
import mother.hackers.gallery.utils.MultipartFileUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/web-api/gallery/albums/{albumId}/photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PutMapping
    public PhotoDto savePhoto(@RequestBody MultipartFile file,
                              @AuthenticationPrincipal AuthenticationUser user) {
        ImageData data = MultipartFileUtils.getData(file);
        return photoService.savePhoto(data, user.getId());
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public List<PhotoDto> getAllAlbumPhotos(@PathVariable("albumId") long albumId,
                                        @AuthenticationPrincipal AuthenticationUser user) {
        return photoService.getAllByAlbumId(albumId, user.getId());
    }

    @GetMapping("/{photoId}")
    public PhotoDto getPhoto(@PathVariable("photoId") long photoId,
                             @AuthenticationPrincipal AuthenticationUser user) {
        return photoService.getPhotoById(photoId, user.getId());
    }

    @PostMapping("/{photoId}/update-status")
    public PhotoDto changePhotoOpenStatus(@PathVariable("photoId") long photoId,
                                          @RequestParam("openStatus") boolean isOpen,
                                          @AuthenticationPrincipal AuthenticationUser user) {
        return photoService.changeOpenStatus(isOpen, photoId, user.getId());
    }

    @PostMapping("/{photoId}/update-description")
    public PhotoDto changePhotoDescription(@PathVariable("photoId") long photoId,
                                           @RequestBody UpdateDescriptionDto dto,
                                           @AuthenticationPrincipal AuthenticationUser user) {
        return photoService.updateDescription(dto, photoId, user.getId());
    }

    @DeleteMapping("/{photoId}")
    public void deletePhoto(@PathVariable("photoId") long photoId,
                            @AuthenticationPrincipal AuthenticationUser user) {
        photoService.deletePhotoById(photoId, user.getId());
    }
}
