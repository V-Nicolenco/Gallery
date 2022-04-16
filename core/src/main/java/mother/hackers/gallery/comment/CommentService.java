package mother.hackers.gallery.comment;

import mother.hackers.gallery.comment.dto.CommentDto;
import mother.hackers.gallery.comment.dto.CreateCommentDto;
import mother.hackers.gallery.comment.dto.EditCommentDto;

import java.util.List;

public interface CommentService {

    CommentDto addComment(CreateCommentDto dto, long photoId, long userId);

    List<CommentDto> getAllCommentsByPhotoId(long photoId, long userId);

    CommentDto editComment(EditCommentDto dto, long photoId, long commentId, long userId);

    void deleteComment(long photoId, long commentId, long userId);
}
