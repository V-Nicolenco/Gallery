package mother.hackers.gallery.comment;

import mother.hackers.gallery.album.AlbumRepository;
import mother.hackers.gallery.comment.dto.CreateCommentDto;
import mother.hackers.gallery.comment.dto.EditCommentDto;
import mother.hackers.gallery.exceptions.ForbiddenException;
import mother.hackers.gallery.exceptions.NotFoundException;
import mother.hackers.gallery.photo.PhotoRepository;
import org.springframework.stereotype.Component;

@Component
public class CommentValidator {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final CommentRepository commentRepository;

    public CommentValidator(AlbumRepository albumRepository, PhotoRepository photoRepository, CommentRepository commentRepository) {
        this.albumRepository = albumRepository;
        this.photoRepository = photoRepository;
        this.commentRepository = commentRepository;
    }

    public void addComment(long albumId, long photoId, CreateCommentDto dto) {
        if (!albumRepository.existsById(albumId)) throw new NotFoundException("Album not found");
        if (!albumRepository.isPublic(albumId)) throw new ForbiddenException("Album is private");

        if (!photoRepository.existsById(photoId)) throw new NotFoundException("Photo not found");
        if (photoRepository.isCommentsClosed(photoId)) throw  new ForbiddenException("Comments is closed");

        if (dto.getText().isBlank()) throw new ForbiddenException("You cannot save empty comment");
    }

    public void getAllCommentsByPhotoId(long albumId, long photoId) {
        if (!albumRepository.existsById(albumId)) throw new NotFoundException("Album not found");
        if (!photoRepository.existsById(photoId)) throw new NotFoundException("Photo not found");
        if (photoRepository.isCommentsClosed(photoId)) throw  new ForbiddenException("Comments is closed");
    }

    public void validateEditComment(long albumId, long photoId, long commentId, EditCommentDto dto, long userId) {
        if (!albumRepository.existsById(albumId)) throw new NotFoundException("Album not found");
        if (!albumRepository.isPublic(albumId)) throw new ForbiddenException("Album is private");

        if (!photoRepository.existsById(photoId)) throw new NotFoundException("Photo not found");
        if (photoRepository.isCommentsClosed(photoId)) throw  new ForbiddenException("Comments is closed");

        if (!commentRepository.existsById(commentId)) throw new NotFoundException("Comment not found");
        if (!commentRepository.isAuthor(commentId, userId)) throw new ForbiddenException("You do not have access to edit this comment");

        if (dto.getText().isBlank()) throw new ForbiddenException("You cannot save empty comment");
    }

    public void validateDeleteComment(long albumId, long photoId, long commentId, long userId) {
        if (!albumRepository.existsById(albumId)) throw new NotFoundException("Album not found");
        if (!photoRepository.existsById(photoId)) throw new NotFoundException("Photo not found");
        if (!commentRepository.existsById(commentId)) throw new NotFoundException("Comment already deleted or never existed");

        if (!commentRepository.isAuthor(commentId, userId)) throw new ForbiddenException("You do not have access to delete this comment");
    }
}
