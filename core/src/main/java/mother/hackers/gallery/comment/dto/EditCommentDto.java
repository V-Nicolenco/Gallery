package mother.hackers.gallery.comment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditCommentDto {

    @ApiModelProperty(notes = "Comment text that will be changed", example = "Very nice post!", required = true)
    private String text;
}
