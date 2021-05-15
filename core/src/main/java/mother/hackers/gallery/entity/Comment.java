package mother.hackers.gallery.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {

    private int id;
    private User author;
    private String text;
}
