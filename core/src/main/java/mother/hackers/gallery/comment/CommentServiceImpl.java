package mother.hackers.gallery.comment;

import mother.hackers.gallery.album.AlbumRepository;
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

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private static final CommentMapper mapper = CommentMapper.INSTANCE;

    private final CommentRepository commentRepository;
    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    private final CommentValidator commentValidator;

    public CommentServiceImpl(CommentRepository commentRepository,
                              AlbumRepository albumRepository,
                              PhotoRepository photoRepository,
                              UserRepository userRepository,
                              CommentValidator commentValidator) {
        this.commentRepository = commentRepository;
        this.albumRepository = albumRepository;
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
        this.commentValidator = commentValidator;
    }

    @Override
    public CommentDto addComment(long albumId, long photoId, CreateCommentDto dto, long userId) {
        commentValidator.addComment(albumId, photoId, dto);

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
    public List<CommentDto> getAllCommentsByPhotoId(long albumId, long photoId) {
        commentValidator.getAllCommentsByPhotoId(albumId, photoId);

        List<Comment> comments = commentRepository.getCommentsByPhotoId(photoId);
        return mapper.toDto(comments);
    }

    @Override
    public CommentDto editComment(long albumId, long photoId, long commentId, EditCommentDto dto, long userId) {
        commentValidator.validateEditComment(albumId, photoId, commentId, dto, userId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found!"));

        comment.setText(dto.getText());
        commentRepository.save(comment);
        return mapper.toDto(comment);
    }

    @Override
    public void deleteComment(long albumId, long photoId, long commentId, long userId) {
        commentValidator.validateDeleteComment(albumId, photoId, commentId, userId);

        commentRepository.deleteById(commentId);
    }
}
