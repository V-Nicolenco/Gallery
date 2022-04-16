package mother.hackers.gallery.profile;

import mother.hackers.gallery.exceptions.NotFoundException;
import mother.hackers.gallery.profile.dto.CreateProfileDto;
import mother.hackers.gallery.profile.dto.EditProfileDto;
import mother.hackers.gallery.profile.dto.ProfileDto;
import mother.hackers.gallery.user.User;
import mother.hackers.gallery.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {

    private static final ProfileMapper mapper = ProfileMapper.INSTANCE;

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    private final ProfileValidator profileValidator;

    public ProfileServiceImpl(ProfileRepository profileRepository,
                              UserRepository userRepository,
                              ProfileValidator profileValidator) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.profileValidator = profileValidator;
    }

    @Override
    public ProfileDto createProfile(CreateProfileDto profileDto, long userId) {
        profileValidator.validateCreate(profileDto);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User could not be found"));

        Profile newProfile = mapper.toEntity(profileDto);
        newProfile.setOwner(user);

        Profile createdProfile = profileRepository.save(newProfile);
        return mapper.toDto(createdProfile);
    }

    @Override
    public ProfileDto getProfile(long profileId, long userId) {
        profileValidator.validateGetById(profileId, userId);

        return profileRepository.findById(profileId)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Profile not found"));
    }

    @Override
    public List<ProfileDto> getMyProfile(long userId) {
        return profileRepository.findProfilesByUserId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfileDto> getPublicProfiles() {
        return profileRepository.findPublicProfiles().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProfileDto editProfile(long profileId, EditProfileDto editProfileDto, long userId) {
        profileValidator.validateEditProfile(profileId, editProfileDto, userId);

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException("Profile not found"));
        profile.setName(editProfileDto.getName());
        profile.setPublic(editProfileDto.isPublic());

        Profile editedProfile = profileRepository.save(profile);
        return mapper.toDto(editedProfile);
    }

    @Override
    public void deleteByProfileId(long profileId, long userId) {
        profileValidator.validateDeleteProfile(profileId, userId);

        profileRepository.deleteById(profileId);
    }
}