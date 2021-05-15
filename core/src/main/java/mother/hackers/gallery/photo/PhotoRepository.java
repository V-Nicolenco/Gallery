package mother.hackers.gallery.photo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Photo p WHERE p.id = :photoId and p.owner.id = :userId")
    boolean isOwner(@Param("photoId") long photoId, @Param("userId") long userId);

    @Query("SELECT p.isOpen FROM Photo p WHERE p.id = :photoId")
    boolean isPublic(@Param("photoId") long photoId);

}
