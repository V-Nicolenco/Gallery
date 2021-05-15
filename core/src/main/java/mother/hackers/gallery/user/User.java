package mother.hackers.gallery.user;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private long id;
    private String email;
    private String passwordHash;
    private Role role;
    private String firstName;
    private String lastName;
}
