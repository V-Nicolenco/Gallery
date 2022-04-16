package mother.hackers.gallery.profile;

import mother.hackers.gallery.profile.dto.CreateProfileDto;
import mother.hackers.gallery.profile.dto.EditProfileDto;
import mother.hackers.gallery.profile.dto.ProfileDto;

import java.util.List;

public interface ProfileService {

    ProfileDto createProfile(CreateProfileDto dto, long userId);

    ProfileDto getProfile(long profileId, long userId);

    ProfileDto getProfileByUserId(long userId);

    List<ProfileDto> getPublicProfiles();

    ProfileDto editProfile(long profileId, EditProfileDto editProfileDto, long userId);

    void deleteByProfileId(long profileId, long userId);
}
