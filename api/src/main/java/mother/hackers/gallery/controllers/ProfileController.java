package mother.hackers.gallery.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mother.hackers.gallery.exceptions.ErrorDto;
import mother.hackers.gallery.profile.ProfileService;
import mother.hackers.gallery.profile.dto.CreateProfileDto;
import mother.hackers.gallery.profile.dto.EditProfileDto;
import mother.hackers.gallery.profile.dto.ProfileDto;
import mother.hackers.gallery.security.AuthenticationUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web-api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @ApiOperation("Create a new profile for an authorized user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created profile"),
            @ApiResponse(code = 400, message = "Illegal input", response = ErrorDto.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProfileDto createProfile(@RequestBody CreateProfileDto dto,
                                    @AuthenticationPrincipal AuthenticationUser user) {
        return profileService.createProfile(dto, user.getId());
    }


    @ApiOperation(value = "Get the existing profile by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully return profile"),
            @ApiResponse(code = 403, message = "You do not have access to this profile", response = ErrorDto.class),
            @ApiResponse(code = 404, message = "Profile not found", response = ErrorDto.class)
    })
    @GetMapping("/{profileId}")
    public ProfileDto getProfile(@PathVariable("profileId") long profileId,
                                 @AuthenticationPrincipal AuthenticationUser user) {
        long userId = user.getId();
        return profileService.getProfile(profileId, userId);
    }


    @ApiOperation(value = "Get a list of profiles of an authorized user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully return the list of profiles of the authorized user")
    })
    @GetMapping("/my")
    public List<ProfileDto> getMyProfiles(@AuthenticationPrincipal AuthenticationUser user) {
        long userId = user.getId();
        return profileService.getMyProfile(userId);
    }


    @ApiOperation(value = "Get a list of public profiles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully return the list of public profiles")
    })
    @GetMapping("/public")
    public List<ProfileDto> getPublicProfiles() {
        return profileService.getPublicProfiles();
    }


    @ApiOperation(value = "Edit profile name and public status by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully edited profile"),
            @ApiResponse(code = 400, message = "Illegal input", response = ErrorDto.class),
            @ApiResponse(code = 403, message = "You do not have rights to edit this profile", response = ErrorDto.class),
            @ApiResponse(code = 404, message = "Profile not found", response = ErrorDto.class)
    })
    @PatchMapping("/{profileId}")
    public ProfileDto editProfile(@PathVariable("profileId") long profileId,
                                  @RequestBody EditProfileDto editProfileDto,
                                  @AuthenticationPrincipal AuthenticationUser user) {
        return profileService.editProfile(profileId, editProfileDto, user.getId());
    }

    @ApiOperation(value = "Delete profile by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Profile deleted successfully"),
            @ApiResponse(code = 403, message = "You do not have rights to delete this profile", response = ErrorDto.class)
    })
    @DeleteMapping("/{profileId}")
    public void deleteProfile(@PathVariable("profileId") long profileId,
                              @AuthenticationPrincipal AuthenticationUser user) {
        profileService.deleteByProfileId(profileId, user.getId());
    }
}