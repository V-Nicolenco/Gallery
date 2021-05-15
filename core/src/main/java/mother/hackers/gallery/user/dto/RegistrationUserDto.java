package mother.hackers.gallery.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationUserDto {

    private String email;
    private String passwordHash;
    private String confirmPasswordHash;
    private String firstName;
    private String lastName;
}
