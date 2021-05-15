package mother.hackers.gallery.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class AuthenticationUser {

    @Id
    @GeneratedValue
    private long id;
    private String email;
    private String passwordHash;
}