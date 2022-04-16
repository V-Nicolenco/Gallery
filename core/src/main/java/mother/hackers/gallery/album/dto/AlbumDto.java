package mother.hackers.gallery.album.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumDto {

    private long id;
    @ApiModelProperty(notes = "Album name", example = "Summer memories", required = true)
    private String name;
    private long ownerId;
    @ApiModelProperty(notes = "Album is public?", required = true)
    private boolean isPublic;
}
