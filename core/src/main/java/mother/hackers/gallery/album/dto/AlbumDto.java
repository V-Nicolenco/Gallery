package mother.hackers.gallery.album.dto;

import lombok.Getter;
import lombok.Setter;
import mother.hackers.gallery.user.dto.UserDto;

@Getter
@Setter
public class AlbumDto {

    private long id;
    private String name;
    private UserDto owner;
    private boolean isPublic;
}
