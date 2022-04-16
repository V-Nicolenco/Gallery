package mother.hackers.gallery.comment;

import mother.hackers.gallery.comment.dto.CommentDto;
import mother.hackers.gallery.comment.dto.CreateCommentDto;
import mother.hackers.gallery.comment.dto.EditCommentDto;
import mother.hackers.gallery.exceptions.ForbiddenException;
import mother.hackers.gallery.exceptions.NotFoundException;
import mother.hackers.gallery.photo.Photo;
import mother.hackers.gallery.photo.PhotoRepository;
import mother.hackers.gallery.user.User;
import mother.hackers.gallery.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private static final CommentMapper mapper = CommentMapper.INSTANCE;

    private final CommentRepository commentRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              PhotoRepository photoRepository,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentDto addComment(CreateCommentDto dto, long photoId, long userId) {
        if (!photoRepository.isPublic(photoId)) {
            throw new ForbiddenException("Comments are closed!");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException("Photo not found"));

        Comment comment = mapper.toEntity(dto);
        comment.setAuthor(user);

        List<Comment> comments = photo.getComments();
        comments.add(comment);
        photoRepository.save(photo);

        return mapper.toDto(comment);
    }

    @Override
    public List<CommentDto> getAllCommentsByPhotoId(long photoId, long userId) {
        if (!photoRepository.existsById(photoId)) {
            throw new NotFoundException("Photo not found");
        }
        if (!photoRepository.isPublic(photoId)) {
            throw new ForbiddenException("Comments are closed!");
        }

        List<Comment> comments = commentRepository.getCommentsByPhotoId(photoId);
        return mapper.toDto(comments);
    }

    @Override
    public CommentDto editComment(EditCommentDto dto, long photoId, long commentId, long userId) {
        if (commentRepository.isAuthor(commentId, userId)) {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new NotFoundException("Comment not found!"));

            comment.setText(dto.getText());
            commentRepository.save(comment);
            return mapper.toDto(comment);
        } else {
            throw new ForbiddenException("You do not have access to edit this comment");
        }
    }

    @Override
    public void deleteComment(long photoId, long commentId, long userId) {
        if (!commentRepository.existsById(commentId)) {
            throw new NotFoundException("Comment already deleted or never existed");
        }

        if (commentRepository.isAuthor(commentId, userId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new ForbiddenException("You do not have access to delete this comment");
        }
    }
}
