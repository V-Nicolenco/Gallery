package mother.hackers.gallery.comment;

import lombok.Getter;
import lombok.Setter;
import mother.hackers.gallery.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private User author;
    private String text;
}
