package mother.hackers.gallery.controllers;

import mother.hackers.gallery.album.AlbumService;
import mother.hackers.gallery.album.dto.AlbumDto;
import mother.hackers.gallery.album.dto.CreateAlbumDto;
import mother.hackers.gallery.security.AuthenticationUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web-api/gallery/")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PutMapping("/albums")
    public AlbumDto createAlbum(@RequestBody CreateAlbumDto dto,
                                @AuthenticationPrincipal AuthenticationUser user) {
        long userId = user.getId();
        return albumService.createAlbum(dto, userId);
    }

    @GetMapping("/albums/{albumId}")
    public AlbumDto getAlbum(@PathVariable("albumId") long albumId,
                             @AuthenticationPrincipal AuthenticationUser user) {
        long userId = user.getId();
        return albumService.getAlbum(albumId, userId);
    }

    @GetMapping("/albums/my")
    public List<AlbumDto> getMyAlbums(@AuthenticationPrincipal AuthenticationUser user) {
        long userId = user.getId();
        return albumService.getMyAlbums(userId);
    }

    @GetMapping("/albums/public")
    public List<AlbumDto> getPublicAlbums() {
        return albumService.getPublicAlbums();
    }

    @GetMapping("/users/{userId}/albums")
    public List<AlbumDto> getPublicAlbums(@PathVariable("userId") long userId) {
        return albumService.getUserPublicAlbums(userId);
    }

    @DeleteMapping("/albums/{albumId}")
    public void deleteAlbum(@PathVariable("albumId") long albumId,
                            @AuthenticationPrincipal AuthenticationUser user) {
         albumService.deleteByAlbumId(albumId, user.getId());
    }
}
