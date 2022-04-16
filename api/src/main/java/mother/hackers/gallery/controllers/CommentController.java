package mother.hackers.gallery.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mother.hackers.gallery.comment.CommentService;
import mother.hackers.gallery.comment.dto.CommentDto;
import mother.hackers.gallery.comment.dto.CreateCommentDto;
import mother.hackers.gallery.comment.dto.EditCommentDto;
import mother.hackers.gallery.exceptions.ErrorDto;
import mother.hackers.gallery.security.AuthenticationUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web-api/profiles/{profileId}/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "Add comment to post.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Added comment successfully"),
            @ApiResponse(code = 400, message = "Illegal input", response = ErrorDto.class),
            @ApiResponse(code = 403, message = "You do not have access to comment this post", response = ErrorDto.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CommentDto addComment(@PathVariable("profileId") long profileId,
                                 @PathVariable("postId") long postId,
                                 @RequestBody CreateCommentDto commentDto,
                                 @AuthenticationPrincipal AuthenticationUser user) {
        return commentService.addComment(profileId, postId, commentDto, user.getId());
    }


    @ApiOperation(value = "Get all comments by post id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a list of comments")
    })
    @GetMapping
    public List<CommentDto> getCommentsByPostId(@PathVariable("profileId") long profileId,
                                                @PathVariable("postId") long postId) {
        return commentService.getAllCommentsByPostId(profileId, postId);
    }


    @ApiOperation(value = "Edit comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Edited comment successfully"),
            @ApiResponse(code = 400, message = "Illegal input", response = ErrorDto.class),
            @ApiResponse(code = 403, message = "You do not have access to edit this comment", response = ErrorDto.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorDto.class)
    })
    @PostMapping("/{commentId}")
    public CommentDto editComment(@PathVariable("profileId") long profileId,
                                  @PathVariable("commentId") long commentId,
                                  @PathVariable("postId") long postId,
                                  @RequestBody EditCommentDto commentDto,
                                  @AuthenticationPrincipal AuthenticationUser user) {
        return commentService.editComment(profileId, postId, commentId, commentDto, user.getId());
    }


    @ApiOperation(value = "Delete comment by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment successfully deleted"),
            @ApiResponse(code = 403, message = "You do not have access to delete this comment", response = ErrorDto.class),
            @ApiResponse(code = 404, message = "Comment already deleted or never existed", response = ErrorDto.class)
    })
    @DeleteMapping("/{commentId}")
    public void deletePost(@PathVariable("profileId") long profileId,
                           @PathVariable("commentId") long commentId,
                           @PathVariable("postId") long postId,
                           @AuthenticationPrincipal AuthenticationUser user) {
        commentService.deleteComment(profileId, postId, commentId, user.getId());
    }
}
