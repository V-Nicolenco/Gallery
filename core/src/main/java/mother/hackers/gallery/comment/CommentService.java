package mother.hackers.gallery.comment;

import mother.hackers.gallery.comment.dto.CommentDto;
import mother.hackers.gallery.comment.dto.CreateCommentDto;
import mother.hackers.gallery.comment.dto.EditCommentDto;

import java.util.List;

public interface CommentService {

    CommentDto addComment(long profileId, long postId, CreateCommentDto dto, long userId);

    List<CommentDto> getAllCommentsByPostId(long profileId, long postId);

    CommentDto editComment(long profileId, long postId, long commentId, EditCommentDto dto, long userId);

    void deleteComment(long profileId, long postId, long commentId, long userId);
}
