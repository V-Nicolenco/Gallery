package mother.hackers.gallery.comment;

import mother.hackers.gallery.comment.dto.CommentDto;
import mother.hackers.gallery.comment.dto.CreateCommentDto;
import mother.hackers.gallery.comment.dto.EditCommentDto;

import java.util.List;

public interface CommentService {

    CommentDto addComment(long albumId, long photoId, CreateCommentDto dto, long userId);

    List<CommentDto> getAllCommentsByPhotoId(long albumId, long photoId);

    CommentDto editComment(long albumId, long photoId, long commentId, EditCommentDto dto, long userId);

    void deleteComment(long albumId, long photoId, long commentId, long userId);
}
