package mother.hackers.gallery.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p where p.id IN (SELECT a.posts FROM Profile a WHERE a.id = :profileId)")
    List<Post> findAllPostsByProfileId(@Param("profileId") long profileId);

    @Query("SELECT CASE WHEN p.author.id = :userId THEN true ELSE false END " +
            "FROM Post p WHERE p.id = :postId")
    boolean isAuthor(@Param("postId") long postId, @Param("userId") long userId);

    @Query("SELECT p.commentsClosed FROM Post p WHERE p.id = :postId")
    boolean isCommentsClosed(@Param("postId") long postId);
}
