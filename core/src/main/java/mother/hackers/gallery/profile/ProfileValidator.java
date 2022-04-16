package mother.hackers.gallery.profile;

import mother.hackers.gallery.exceptions.ForbiddenException;
import mother.hackers.gallery.exceptions.NotFoundException;
import mother.hackers.gallery.profile.dto.CreateProfileDto;
import mother.hackers.gallery.profile.dto.EditProfileDto;
import org.springframework.stereotype.Component;


// ToDo all of these checks must be done before they go to the controller
@Component
public class ProfileValidator {

    private final ProfileRepository profileRepository;

    ProfileValidator(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    void validateCreate(CreateProfileDto profileDto) {
        if (profileDto.getName().isBlank()) throw new IllegalArgumentException("Cannot create profile with empty name");
    }

    void validateGetById(long profileId, long userId) {
        if (!profileRepository.existsById(profileId)) throw new NotFoundException("Profile not found");
        if (!profileRepository.isPublic(profileId) && !profileRepository.isOwner(profileId, userId))
            throw new ForbiddenException("You do not have access to this profile");
    }

    void validateEditProfile(long profileId, EditProfileDto profileDto, long userId) {
        if (!profileRepository.existsById(profileId)) throw new NotFoundException("Profile not found");
        if (!profileRepository.isOwner(profileId, userId))
            throw new ForbiddenException("You do not have access to edit this profile");

        if (profileDto.getName().isBlank()) throw new IllegalArgumentException("Cannot set empty name to profile");
    }

    void validateDeleteProfile(long profileId, long userId) {
        if (!profileRepository.existsById(profileId)) throw new NotFoundException("Profile not found");
        if (!profileRepository.isOwner(profileId, userId))
            throw new ForbiddenException("You do not have access to delete this profile");
    }
}
