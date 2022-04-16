package mother.hackers.gallery.post;

import mother.hackers.gallery.exceptions.ForbiddenException;
import mother.hackers.gallery.exceptions.NotFoundException;
import mother.hackers.gallery.post.dto.CreatePostDto;
import mother.hackers.gallery.profile.ProfileRepository;
import org.springframework.stereotype.Component;

@Component
public class PostValidator {

    private final ProfileRepository profileRepository;
    private final PostRepository postRepository;

    public PostValidator(ProfileRepository profileRepository, PostRepository postRepository) {
        this.profileRepository = profileRepository;
        this.postRepository = postRepository;
    }

    void validateSavePost(long profileId, CreatePostDto createPostDto, long userId) {
        if (!profileRepository.existsById(profileId)) throw new NotFoundException("Profile not found");
        if (!profileRepository.isOwner(profileId, userId))
            throw new ForbiddenException("You do not have access to upload posts to this profile");

        if (createPostDto.getDescription().isBlank()) throw new IllegalArgumentException("Cannot create empty post");
    }

    void validateGetPostById(long profileId, long postId, long userId) {
        if (!profileRepository.existsById(profileId)) throw new NotFoundException("Profile not found");
        if (!profileRepository.isPublic(profileId) && !profileRepository.isOwner(profileId, userId))
            throw new ForbiddenException("You do not have access to see posts in this profile");

        if (!postRepository.existsById(postId)) throw new NotFoundException("Post not found");
    }

    void validateGetAllPostsByProfileId(long profileId, long userId) {
        if (!profileRepository.existsById(profileId)) throw new NotFoundException("Profile not found");

        if (profileRepository.isPublic(profileId) || profileRepository.isOwner(profileId, userId))
            throw new ForbiddenException("This profile is private and only owner can see posts in it");
    }

    public void validateUpdateDescription(long profileId, long postId, long userId) {
        if (!profileRepository.existsById(profileId)) throw new NotFoundException("Profile not found");
        if (!postRepository.existsById(postId)) throw new NotFoundException("Post not found");

        if (!postRepository.isAuthor(postId, userId))
            throw new ForbiddenException("Only the owner can change the description of this post.");
    }

    public void validateChangePostCommentsStatus(long profileId, long postId, long userId) {
        if (!profileRepository.existsById(profileId)) throw new NotFoundException("Profile not found");
        if (!postRepository.existsById(postId)) throw new NotFoundException("Post not found");

        if (!postRepository.isAuthor(postId, userId))
            throw new ForbiddenException("Only the owner can change the status of this post.");
    }

    void validateDeletePost(long profileId, long postId, long userId) {
        if (!profileRepository.existsById(profileId)) throw new NotFoundException("Profile not found");
        if (!postRepository.existsById(postId)) throw new NotFoundException("Post not found");

        if (!postRepository.isAuthor(postId, userId))
            throw new ForbiddenException("Only the owner can delete this post.");
    }
}
