package mother.hackers.gallery.photo;

import lombok.Getter;
import lombok.Setter;
import mother.hackers.gallery.comment.Comment;
import mother.hackers.gallery.photo.dto.ImageData;
import mother.hackers.gallery.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Getter
@Setter
@Entity
public class Photo {

    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    private ImageData data;
    private String description;
    private boolean isPublic;
    @ManyToOne
    private User owner;
    @OneToMany
    private List<Comment> comments;
}
