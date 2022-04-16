package mother.hackers.gallery.post.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostDto {

    @ApiModelProperty(notes = "Post description that will be added")
    private String description;
    @ApiModelProperty(notes = "Comments is closed?", required = true, value = "false")
    private boolean commentsClosed;
}
