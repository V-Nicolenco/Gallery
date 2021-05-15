package mother.hackers.gallery.controllers;

import mother.hackers.gallery.photo.PhotoService;
import mother.hackers.gallery.photo.dto.PhotoDto;
import mother.hackers.gallery.photo.dto.SavePhotoDto;
import mother.hackers.gallery.photo.dto.UpdateDescriptionDto;
import mother.hackers.gallery.security.AuthenticationUser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web-api/gallery/albums/{albumId}/photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PutMapping
    public PhotoDto savePhoto(@RequestBody SavePhotoDto dto, AuthenticationUser user) {
        return photoService.savePhoto(dto, user.getId());
    }

    @GetMapping("/{photoId}")
    public PhotoDto getPhoto(@PathVariable("photoId") long photoId, AuthenticationUser user) {
        return photoService.getPhotoById(photoId, user.getId());
    }

    @PostMapping("/{photoId}/update-status")
    public PhotoDto changePhotoOpenStatus(@PathVariable("photoId") long photoId,
                                     @RequestParam("openStatus") boolean isOpen,
                                     AuthenticationUser user) {
        return photoService.changeOpenStatus(isOpen, photoId, user.getId());
    }

    @PostMapping("/{photoId}/update-description")
    public PhotoDto changePhotoDescription(@PathVariable("photoId") long photoId,
                                      @RequestBody UpdateDescriptionDto dto,
                                      AuthenticationUser user) {
        return photoService.updateDescription(dto, photoId, user.getId());
    }

    @DeleteMapping("/{photoId}")
    public void deletePhoto(@PathVariable("photoId") long photoId, AuthenticationUser user) {
        photoService.deletePhotoById(photoId, user.getId());
    }
}
