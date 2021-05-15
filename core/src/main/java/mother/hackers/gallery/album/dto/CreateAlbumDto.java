package mother.hackers.gallery.album.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAlbumDto {

    private String name;
    private boolean isPublic;
}
