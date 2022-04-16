package mother.hackers.gallery.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("SELECT a FROM Profile a WHERE a.owner.id = :userId")
    Optional<Profile> findProfileByUserId(@Param("userId") long userId);

    @Query("SELECT a FROM Profile a WHERE a.isPublic = true")
    List<Profile> findPublicProfiles();

    @Query("SELECT CASE WHEN a.owner.id = :userId THEN true ELSE false END " +
            "FROM Profile a WHERE a.id = :profileId")
    boolean isOwner(@Param("profileId") long profileId, @Param("userId") long userId);

    @Query("SELECT a.isPublic FROM Profile a WHERE a.id = :profileId")
    boolean isPublic(@Param("profileId") long profileId);
}
