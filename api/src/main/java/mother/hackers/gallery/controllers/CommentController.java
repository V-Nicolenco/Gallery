package mother.hackers.gallery.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mother.hackers.gallery.comment.CommentService;
import mother.hackers.gallery.comment.dto.CommentDto;
import mother.hackers.gallery.comment.dto.CreateCommentDto;
import mother.hackers.gallery.comment.dto.EditCommentDto;
import mother.hackers.gallery.security.AuthenticationUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @ApiOperation(value = "Add comment to photo.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Added comment successfully"),
            @ApiResponse(code = 403, message = "Comments are closed"),
            @ApiResponse(code = 404, message = "Photo not found")
    })
    @PutMapping
    public CommentDto addComment(@RequestBody CreateCommentDto dto,
                                       @PathVariable("photoId") long photoId,
                                       @AuthenticationPrincipal AuthenticationUser user) {
        return commentService.addComment(dto, photoId, user.getId());
    }

    @ApiOperation(value = "Get all comments by photo id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a list of comments"),
            @ApiResponse(code = 403, message = "Comments are closed"),
            @ApiResponse(code = 404, message = "Photo not found")
    })
    @GetMapping
    public List<CommentDto> getCommentsByPhotoId(@PathVariable("photoId") long photoId,
                                 @AuthenticationPrincipal AuthenticationUser user) {
        return commentService.getAllCommentsByPhotoId(photoId, user.getId());
    }

    @ApiOperation(value = "Edit comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Edited comment successfully"),
            @ApiResponse(code = 403, message = "You do not have access to edit this comment"),
            @ApiResponse(code = 404, message = "Comment not found")
    })
    @PostMapping("/{commentId}")
    public CommentDto editComment(@PathVariable("commentId") long commentId,
                                        @PathVariable("photoId") long photoId,
                                        @RequestBody EditCommentDto dto,
                                        @AuthenticationPrincipal AuthenticationUser user) {
        return commentService.editComment(dto, photoId, commentId, user.getId());
    }

    @ApiOperation(value = "Delete comment by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment successfully deleted"),
            @ApiResponse(code = 403, message = "You do not have access to delete this comment"),
            @ApiResponse(code = 404, message = "Comment already deleted or never existed")
    })
    @DeleteMapping("/{commentId}")
    public void deletePhoto(@PathVariable("commentId") long commentId,
                                        @PathVariable("photoId") long photoId,
                                        @AuthenticationPrincipal AuthenticationUser user) {
        commentService.deleteComment(photoId, commentId, user.getId());
    }
}
