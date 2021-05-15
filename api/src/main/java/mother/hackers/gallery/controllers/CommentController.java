package mother.hackers.gallery.controllers;

import mother.hackers.gallery.comment.CommentService;
import mother.hackers.gallery.comment.dto.CommentDto;
import mother.hackers.gallery.comment.dto.CreateCommentDto;
import mother.hackers.gallery.comment.dto.EditCommentDto;
import mother.hackers.gallery.security.AuthenticationUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web-api/gallery/albums/{albumId}/photos/{photoId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PutMapping
    public List<CommentDto> addComment(@RequestBody CreateCommentDto dto,
                                       @PathVariable("photoId") long photoId,
                                       @AuthenticationPrincipal AuthenticationUser user) {
        return commentService.addComment(dto, photoId, user.getId());
    }

    @PostMapping("/{commentId}")
    public List<CommentDto> editComment(@PathVariable("commentId") long commentId,
                                        @PathVariable("photoId") long photoId,
                                        @RequestBody EditCommentDto dto,
                                        @AuthenticationPrincipal AuthenticationUser user) {
        return commentService.editComment(dto, photoId, commentId, user.getId());
    }

    @DeleteMapping("/{commentId}")
    public List<CommentDto> deletePhoto(@PathVariable("commentId") long commentId,
                                        @PathVariable("photoId") long photoId,
                                        @AuthenticationPrincipal AuthenticationUser user) {
        return commentService.deleteComment(photoId, commentId, user.getId());
    }
}
