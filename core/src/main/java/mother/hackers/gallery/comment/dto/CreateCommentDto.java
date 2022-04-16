package mother.hackers.gallery.comment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentDto {

    @ApiModelProperty(notes = "Comment text that will be added", example = "Nice photo!", required = true)
    private String text;
}
