package mother.hackers.gallery.album.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditAlbumDto {

    @ApiModelProperty(notes = "Album name", example = "Summer memories", required = true)
    private String name;
    @ApiModelProperty(notes = "Album is public?", required = true)
    private boolean isPublic;
}
