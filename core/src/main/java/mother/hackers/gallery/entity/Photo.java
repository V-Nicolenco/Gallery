package mother.hackers.gallery.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Photo {

    private int id;
    private String link;
    private String description;
    private boolean isOpen;
    private List<Comment> comments;
}
