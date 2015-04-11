package so.glad.channel.edrive.springsocial;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;

import org.springframework.social.connect.UserProfileBuilder;
import so.glad.channel.edrive.api.EDrive;

import so.glad.channel.edrive.entity.UserProfile;

/**
 * EDrive ApiAdapter implementation.
 * @author Palmtale
 */
public class EDriveAdapter implements ApiAdapter<EDrive> {

    public boolean test(EDrive eDrive) {
        try {
            eDrive.userOperations().getUserInfo();
            return true;
        } catch (ApiException e) {
            return false;
        }
    }

    public void setConnectionValues(EDrive eDrive, ConnectionValues values) {
        UserProfile userProfile = eDrive.userOperations().getUserProfile();
        values.setProviderUserId(userProfile.getLoginName());
        values.setDisplayName(userProfile.getNickname());
        values.setProfileUrl("http://cloud.189.cn/u/" + userProfile.getLoginName().substring(0, userProfile.getLoginName().indexOf('@')));
        values.setImageUrl(userProfile.getHeadPortraitUrl());
    }

    public org.springframework.social.connect.UserProfile fetchUserProfile(EDrive eDrive) {
        UserProfile userProfile = eDrive.userOperations().getUserProfile();
        return new UserProfileBuilder().setName(userProfile.getNickname()).setFirstName(userProfile.getRealname()).setLastName(userProfile.getRealname()).
                setEmail(userProfile.getEmail()).setUsername(userProfile.getLoginName()).build();
    }

    public void updateStatus(EDrive eDrive, String message) {
        //TODO
    }

}
