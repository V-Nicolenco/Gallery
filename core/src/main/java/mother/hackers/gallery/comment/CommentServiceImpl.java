package mother.hackers.gallery.comment;

import mother.hackers.gallery.comment.dto.CommentDto;
import mother.hackers.gallery.comment.dto.CreateCommentDto;
import mother.hackers.gallery.comment.dto.EditCommentDto;
import mother.hackers.gallery.exceptions.NotFoundException;
import mother.hackers.gallery.post.Post;
import mother.hackers.gallery.post.PostRepository;
import mother.hackers.gallery.profile.ProfileRepository;
import mother.hackers.gallery.user.User;
import mother.hackers.gallery.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private static final CommentMapper mapper = CommentMapper.INSTANCE;

    private final CommentRepository commentRepository;
    private final ProfileRepository profileRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final CommentValidator commentValidator;

    public CommentServiceImpl(CommentRepository commentRepository,
                              ProfileRepository profileRepository,
                              PostRepository postRepository,
                              UserRepository userRepository,
                              CommentValidator commentValidator) {
        this.commentRepository = commentRepository;
        this.profileRepository = profileRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentValidator = commentValidator;
    }

    @Override
    public CommentDto addComment(long profileId, long postId, CreateCommentDto dto, long userId) {
        commentValidator.addComment(profileId, postId, dto);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        Comment comment = mapper.toEntity(dto);
        comment.setAuthor(user);

        List<Comment> comments = post.getComments();
        comments.add(comment);
        postRepository.save(post);

        return mapper.toDto(comment);
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(long profileId, long postId) {
        commentValidator.getAllCommentsBypostId(profileId, postId);

        List<Comment> comments = commentRepository.getCommentsByPostId(postId);
        return mapper.toDto(comments);
    }

    @Override
    public CommentDto editComment(long profileId, long postId, long commentId, EditCommentDto dto, long userId) {
        commentValidator.validateEditComment(profileId, postId, commentId, dto, userId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found!"));

        comment.setText(dto.getText());
        commentRepository.save(comment);
        return mapper.toDto(comment);
    }

    @Override
    public void deleteComment(long profileId, long postId, long commentId, long userId) {
        commentValidator.validateDeleteComment(profileId, postId, commentId, userId);

        commentRepository.deleteById(commentId);
    }
}
