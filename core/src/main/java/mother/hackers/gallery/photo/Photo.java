package mother.hackers.gallery.photo;

import lombok.Getter;
import lombok.Setter;
import mother.hackers.gallery.comment.Comment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
public class Photo {

    @Id
    @GeneratedValue
    private long id;
    private String link;
    private String description;
    private boolean isOpen;
    @OneToMany
    private List<Comment> comments;
}
