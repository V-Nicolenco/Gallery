package mother.hackers.gallery.comment;

import mother.hackers.gallery.comment.dto.CommentDto;
import mother.hackers.gallery.comment.dto.CreateCommentDto;
import mother.hackers.gallery.comment.dto.EditCommentDto;
import mother.hackers.gallery.photo.dto.PhotoDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> addComment(CreateCommentDto dto, long photoId, long userId);

    List<CommentDto> editComment(EditCommentDto dto, long photoId, long commentId, long userId);

    List<CommentDto> deleteComment(long photoId, long commentId, long userId);
}
