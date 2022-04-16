package mother.hackers.gallery.post;

import mother.hackers.gallery.post.dto.CreatePostDto;
import mother.hackers.gallery.post.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(long profileId, CreatePostDto createPostDto, long userId);

    PostDto getPostById(long profileId, long postId, long id);

    List<PostDto> getAllByProfileId(long profileId, long userId);

    PostDto changePostCommentsStatus(long profileId, long postId, boolean closed, long id);

    PostDto updateDescription(long profileId, long postId, String description, long id);

    void deletePostById(long profileId, long postId, long id);
}
