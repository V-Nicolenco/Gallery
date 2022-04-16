package mother.hackers.gallery.profile.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProfileDto {

    @ApiModelProperty(notes = "Profile name", example = "Geekhub profile", required = true)
    private String name;
    @ApiModelProperty(notes = "Profile is public?", required = true)
    private boolean isPublic;
}
