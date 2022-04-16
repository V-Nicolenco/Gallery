package mother.hackers.gallery.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT CASE WHEN c.author.id = :userId THEN true ELSE false END " +
            "FROM Comment c WHERE c.id = :commentId")
    boolean isAuthor(@Param("commentId") long commentId, @Param("userId") long userId);

    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId")
    List<Comment> getCommentsByPostId(@Param("postId") long postId);
}
