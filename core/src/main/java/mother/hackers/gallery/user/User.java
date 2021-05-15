package mother.hackers.gallery.user;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
}
