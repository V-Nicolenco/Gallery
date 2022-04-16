package mother.hackers.gallery.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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


    @ApiOperation("Create a new album for an authorized user")
    @PutMapping("/albums")
    public AlbumDto createAlbum(@RequestBody CreateAlbumDto dto,
                                @AuthenticationPrincipal AuthenticationUser user) {
        long userId = user.getId();
        return albumService.createAlbum(dto, userId);
    }


    @ApiOperation(value = "Get the existing album by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully return album"),
            @ApiResponse(code = 403, message = "You do not have access to this album"),
            @ApiResponse(code = 404, message = "Album not found")
    })
    @GetMapping("/albums/{albumId}")
    public AlbumDto getAlbum(@PathVariable("albumId") long albumId,
                             @AuthenticationPrincipal AuthenticationUser user) {
        long userId = user.getId();
        return albumService.getAlbum(albumId, userId);
    }


    @ApiOperation(value = "Get a list of albums of an authorized user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully return the list of albums of the authorized user")
    })
    @GetMapping("/albums/my")
    public List<AlbumDto> getMyAlbums(@AuthenticationPrincipal AuthenticationUser user) {
        long userId = user.getId();
        return albumService.getMyAlbums(userId);
    }


    @ApiOperation(value = "Get a list of public albums")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully return the list of public albums")
    })
    @GetMapping("/albums/public")
    public List<AlbumDto> getPublicAlbums() {
        return albumService.getPublicAlbums();
    }


    @ApiOperation(value = "Get a list of a user's public albums")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully return a list of the user's public albums")
    })
    @GetMapping("/users/{userId}/albums")
    public List<AlbumDto> getPublicAlbums(@PathVariable("userId") long userId) {
        return albumService.getUserPublicAlbums(userId);
    }


    @ApiOperation(value = "Delete an album by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Album deleted successfully"),
            @ApiResponse(code = 403, message = "You do not have rights to delete this album")
    })
    @DeleteMapping("/albums/{albumId}")
    public void deleteAlbum(@PathVariable("albumId") long albumId,
                            @AuthenticationPrincipal AuthenticationUser user) {
        albumService.deleteByAlbumId(albumId, user.getId());
    }
}