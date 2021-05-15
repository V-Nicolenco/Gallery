package mother.hackers.gallery.album;

import lombok.Getter;
import lombok.Setter;
import mother.hackers.gallery.photo.Photo;
import mother.hackers.gallery.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
public class Album {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    @ManyToOne
    private User owner;
    @OneToMany
    private List<Photo> photos;
    private boolean isPublic;
}
