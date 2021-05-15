package mother.hackers.gallery.comment.dto;

import lombok.Getter;
import lombok.Setter;
import mother.hackers.gallery.user.dto.UserDto;

@Getter
@Setter
public class CommentDto {

    private long id;
    private UserDto author;
    private String text;
}
