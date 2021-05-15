package mother.hackers.gallery.album.dto;

import lombok.Getter;
import lombok.Setter;
import mother.hackers.gallery.photo.dto.PhotoDto;
import mother.hackers.gallery.user.dto.UserDto;

import java.util.List;

@Getter
@Setter
public class AlbumDto {

    private long id;
    private String name;
    private UserDto owner;
    private List<PhotoDto> photos;
    private boolean isPublic;
}
