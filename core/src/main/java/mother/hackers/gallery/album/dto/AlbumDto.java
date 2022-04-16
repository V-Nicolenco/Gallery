package mother.hackers.gallery.album.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumDto {

    private long id;
    private String name;
    private long ownerId;
    private boolean isPublic;
}
