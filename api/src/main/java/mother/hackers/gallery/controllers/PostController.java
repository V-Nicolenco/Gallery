package mother.hackers.gallery.controllers;

import mother.hackers.gallery.post.PostService;
import mother.hackers.gallery.post.dto.CreatePostDto;
import mother.hackers.gallery.post.dto.PostDto;
import mother.hackers.gallery.security.AuthenticationUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web-api/profiles/{profileId}/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public PostDto createPost(@PathVariable("profileId") long profileId,
                              @RequestBody CreatePostDto createPostDto,
                              @AuthenticationPrincipal AuthenticationUser user) {
        return postService.createPost(profileId, createPostDto, user.getId());
    }

    @GetMapping
    public List<PostDto> getAllProfilePosts(@PathVariable("profileId") long profileId,
                                            @AuthenticationPrincipal AuthenticationUser user) {
        return postService.getAllByProfileId(profileId, user.getId());
    }

    @GetMapping("/{postId}")
    public PostDto getPost(@PathVariable("profileId") long profileId,
                           @PathVariable("postId") long postId,
                           @AuthenticationPrincipal AuthenticationUser user) {
        return postService.getPostById(profileId, postId, user.getId());
    }

    @PostMapping("/{postId}/comments/status")
    public PostDto changePostCommentsStatus(@PathVariable("profileId") long profileId,
                                            @PathVariable("postId") long postId,
                                            @RequestParam("closed") boolean closed,
                                            @AuthenticationPrincipal AuthenticationUser user) {
        return postService.changePostCommentsStatus(profileId, postId, closed, user.getId());
    }

    @PostMapping("/{postId}/description")
    public PostDto changePostDescription(@PathVariable("profileId") long profileId,
                                         @PathVariable("postId") long postId,
                                         @RequestBody String description,
                                         @AuthenticationPrincipal AuthenticationUser user) {
        return postService.updateDescription(profileId, postId, description, user.getId());
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable("profileId") long profileId,
                           @PathVariable("postId") long postId,
                           @AuthenticationPrincipal AuthenticationUser user) {
        postService.deletePostById(profileId, postId, user.getId());
    }
}
