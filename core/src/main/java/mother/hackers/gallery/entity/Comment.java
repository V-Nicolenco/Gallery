package mother.hackers.gallery.entity;

import lombok.Getter;
import lombok.Setter;

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
