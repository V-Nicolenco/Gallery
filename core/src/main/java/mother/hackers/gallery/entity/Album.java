package mother.hackers.gallery.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Album {

    private int id;
    private User owner;
    private List<Photo> photos;
    private boolean isPublic;
}
