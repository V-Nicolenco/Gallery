package mother.hackers.gallery.comment;

import mother.hackers.gallery.comment.dto.CreateCommentDto;
import mother.hackers.gallery.comment.dto.EditCommentDto;
import mother.hackers.gallery.exceptions.ForbiddenException;
import mother.hackers.gallery.exceptions.NotFoundException;
import mother.hackers.gallery.post.PostRepository;
import mother.hackers.gallery.profile.ProfileRepository;
import org.springframework.stereotype.Component;

// ToDo all of these checks must be done before they go to the controller
@Component
public class CommentValidator {

    private final ProfileRepository profileRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    CommentValidator(ProfileRepository profileRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.profileRepository = profileRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    void addComment(long profileId, long postId, CreateCommentDto dto) {
        if (!profileRepository.existsById(profileId)) throw new NotFoundException("Profile not found");
        if (!profileRepository.isPublic(profileId)) throw new ForbiddenException("Profile is private");

        if (!postRepository.existsById(postId)) throw new NotFoundException("Post not found");
        if (postRepository.isCommentsClosed(postId)) throw new ForbiddenException("Comments is closed");

        if (dto.getText().isBlank()) throw new IllegalArgumentException("You cannot save empty comment");
    }

    void getAllCommentsBypostId(long profileId, long postId) {
        if (!profileRepository.existsById(profileId)) throw new NotFoundException("Profile not found");
        if (!postRepository.existsById(postId)) throw new NotFoundException("Post not found");
        if (postRepository.isCommentsClosed(postId)) throw new ForbiddenException("Comments is closed");
    }

    void validateEditComment(long profileId, long postId, long commentId, EditCommentDto dto, long userId) {
        if (!profileRepository.existsById(profileId)) throw new NotFoundException("Profile not found");
        if (!profileRepository.isPublic(profileId)) throw new ForbiddenException("Profile is private");

        if (!postRepository.existsById(postId)) throw new NotFoundException("Post not found");
        if (postRepository.isCommentsClosed(postId)) throw new ForbiddenException("Comments is closed");

        if (!commentRepository.existsById(commentId)) throw new NotFoundException("Comment not found");
        if (!commentRepository.isAuthor(commentId, userId))
            throw new ForbiddenException("You do not have access to edit this comment");

        if (dto.getText().isBlank()) throw new ForbiddenException("You cannot save empty comment");
    }

    void validateDeleteComment(long profileId, long postId, long commentId, long userId) {
        if (!profileRepository.existsById(profileId)) throw new NotFoundException("Profile not found");
        if (!postRepository.existsById(postId)) throw new NotFoundException("Post not found");
        if (!commentRepository.existsById(commentId))
            throw new NotFoundException("Comment already deleted or never existed");

        if (!commentRepository.isAuthor(commentId, userId))
            throw new ForbiddenException("You do not have access to delete this comment");
    }
}
