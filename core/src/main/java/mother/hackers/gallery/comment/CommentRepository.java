package mother.hackers.gallery.comment;

import mother.hackers.gallery.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM Comment c WHERE c.id = :commentId and c.author.id = :userId")
    boolean isAuthor(@Param("commentId") long commentId, @Param("userId") long userId);

    @Query("SELECT * FROM Comment c WHERE c.id IN (SELECT p.comments.id FROM Photo p WHERE p.id = :photoId)")
    List<Comment> getCommentsByPhotoId(@Param("photoId") long photoId);
}
