package mother.hackers.gallery.profile.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {

    private long id;
    @ApiModelProperty(notes = "Profile name", example = "Geekhub profile", required = true)
    private String name;
    private long ownerId;
    @ApiModelProperty(notes = "Profile is public?", required = true)
    private boolean isPublic;
}
