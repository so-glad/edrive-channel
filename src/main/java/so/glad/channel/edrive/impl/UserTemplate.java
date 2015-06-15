package so.glad.channel.edrive.impl;

import so.glad.channel.edrive.Const;
import so.glad.channel.edrive.EdriveConfig;
import so.glad.channel.edrive.api.UserOperations;
import so.glad.channel.edrive.entity.UserInfo;
import so.glad.channel.edrive.entity.UserProfile;
import so.glad.client.http.HttpMethod;
import so.glad.serializer.StreamSerializer;


/**
 * @author Cartoon
 * on 2015/3/26.
 */
public class UserTemplate extends AbstractOperations implements UserOperations {

    private StreamSerializer streamSerializer;

    public UserTemplate(String appKey, String appSecret) {
        super(appKey, appSecret);
    }

    @Override
    public UserInfo getUserInfo() {
        if(this.getAuthorizeParameters() == null){
            throw new RuntimeException("Did not authorized");
        }
        String command =  EdriveConfig.getProperty(Const.ConfigKey.ACT_USER_INFO);
        return operate(command, "", HttpMethod.GET, UserInfo.class);
    }

    @Override
    public UserProfile getUserProfile() {
        if(this.getAuthorizeParameters() == null){
            throw new RuntimeException("Did not authorized");
        }
        String command =  EdriveConfig.getProperty(Const.ConfigKey.ACT_USER_PROFILE);
        return operate(command, "", HttpMethod.GET, UserProfile.class);
    }

    @Override
    public UserProfile uploadUserHeadProtait(byte[] portrait) {
        if(this.getAuthorizeParameters() == null){
            throw new RuntimeException("Did not authorized");
        }
        String command =  EdriveConfig.getProperty(Const.ConfigKey.ACT_USER_UPLOAD);
        return operate(command, "", HttpMethod.POST, UserProfile.class);
    }
}
