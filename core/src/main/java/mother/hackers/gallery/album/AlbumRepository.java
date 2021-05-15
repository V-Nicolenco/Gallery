package mother.hackers.gallery.album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("SELECT * FROM Album a WHERE a.owner.id = :userId")
    List<Album> findAlbumsByUserId(@Param("userId") long userId);

    @Query("SELECT * FROM Album a WHERE a.owner.id = :userId and a.isPublic = true")
    List<Album> findOpenUserAlbumsByUserId(@Param("userId") long userId);

    @Query("SELECT * FROM Album a WHERE a.isPublic = true")
    List<Album> findOpenAlbums();

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM Album a WHERE a.id = :albumId and a.owner.id = :userId")
    boolean isOwner(@Param("albumId") long albumId, @Param("userId") long userId);
}
