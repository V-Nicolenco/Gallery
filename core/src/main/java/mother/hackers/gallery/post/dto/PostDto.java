package mother.hackers.gallery.post.dto;

import lombok.Getter;
import lombok.Setter;
import mother.hackers.gallery.comment.dto.CommentDto;

import java.util.List;

@Getter
@Setter
public class PostDto {

    private long id;
    private String description;
    private boolean commentsClosed;
    private int authorId;
    private List<CommentDto> comments;
}
