package mother.hackers.gallery.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private int id;
    private String email;
    private String passwordHash;
}
