package mother.hackers.gallery.post;

import mother.hackers.gallery.exceptions.NotFoundException;
import mother.hackers.gallery.post.dto.CreatePostDto;
import mother.hackers.gallery.post.dto.PostDto;
import mother.hackers.gallery.profile.ProfileRepository;
import mother.hackers.gallery.user.User;
import mother.hackers.gallery.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private static final PostMapper mapper = PostMapper.INSTANCE;

    private final ProfileRepository profileRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final PostValidator postValidator;

    public PostServiceImpl(ProfileRepository profileRepository,
                           PostRepository postRepository,
                           UserRepository userRepository,
                           PostValidator postValidator) {
        this.profileRepository = profileRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postValidator = postValidator;
    }

    @Override
    public PostDto createPost(long profileId, CreatePostDto createPostDto, long userId) {
        postValidator.validateSavePost(profileId, createPostDto, userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User could not be found"));

        Post newPost = new Post();
        newPost.setAuthor(user);
        newPost.setDescription(createPostDto.getDescription());
        newPost.setCommentsClosed(createPostDto.isCommentsClosed());
        Post savedPost = postRepository.save(newPost);

        profileRepository.findById(profileId).ifPresent(profile -> {
            profile.getPosts().add(savedPost);
            profileRepository.save(profile);
        });

        return mapper.toDto(savedPost);
    }

    @Override
    public PostDto getPostById(long profileId, long postId, long userId) {
        postValidator.validateGetPostById(profileId, postId, userId);

        return postRepository.findById(postId)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Post could not be found"));
    }

    @Override
    public List<PostDto> getAllByProfileId(long profileId, long userId) {
        postValidator.validateGetAllPostsByProfileId(profileId, userId);

        return postRepository.findAllPostsByProfileId(profileId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto changePostCommentsStatus(long profileId, long postId, boolean commentsClosed, long userId) {
        postValidator.validateChangePostCommentsStatus(profileId, postId, userId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post could not be found"));
        post.setCommentsClosed(commentsClosed);

        Post updatedPost = postRepository.save(post);
        return mapper.toDto(updatedPost);
    }

    @Override
    public PostDto updateDescription(long profileId, long postId, String description, long userId) {
        postValidator.validateUpdateDescription(profileId, postId, userId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post could not be found"));
        post.setDescription(description);

        Post updatedPost = postRepository.save(post);
        return mapper.toDto(updatedPost);
    }

    @Override
    public void deletePostById(long profileId, long postId, long userId) {
        postValidator.validateDeletePost(profileId, postId, userId);

        postRepository.deleteById(postId);
    }
}
