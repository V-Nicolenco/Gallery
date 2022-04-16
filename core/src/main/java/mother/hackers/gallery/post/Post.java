package mother.hackers.gallery.post;

import lombok.Getter;
import lombok.Setter;
import mother.hackers.gallery.comment.Comment;
import mother.hackers.gallery.profile.Profile;
import mother.hackers.gallery.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private boolean commentsClosed;
    @ManyToOne
    private User author;
    @ManyToOne
    private Profile profile;
}
