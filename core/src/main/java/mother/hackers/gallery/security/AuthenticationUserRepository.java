package mother.hackers.gallery.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationUserRepository extends JpaRepository<AuthenticationUser, Long> {

    @Query("SELECT * FROM AuthenticationUser a WHERE a.email = :email")
    Optional<AuthenticationUser> findUserByEmail(@Param("email") String email);
}
