package mother.hackers.gallery.comment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private long id;
    private long authorId;
    @ApiModelProperty(notes = "Comment text that will be added", example = "Nice post", required = true)
    private String text;
}
