package so.glad.channel.edrive.springsocial;

import org.springframework.web.client.RestTemplate;
import so.glad.channel.edrive.Const;
import so.glad.channel.edrive.EdriveConfig;
import so.glad.channel.edrive.api.UserOperations;
import so.glad.channel.edrive.entity.UserInfo;
import so.glad.channel.edrive.entity.UserProfile;
import so.glad.client.http.HttpMethod;

import java.net.URI;

/**
 * Created by palmtale on 2015/3/26.
 */
public class UserTemplate extends AbstractOperations implements UserOperations {

    public UserTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(isAuthorized);
        super.setRestTemplate(restTemplate);
    }
    @Override
    public UserInfo getUserInfo() {
        requireAuthorization();
        String command =  EdriveConfig.getProperty(Const.ConfigKey.ACT_USER_INFO);
        String url = EdriveConfig.getProperty(Const.ConfigKey.API_URL) + command;
        getLogger().debug(command + " - url=" + url);
        return getRestTemplate().getForObject(URI.create(url), UserInfo.class);
    }

    @Override
    public UserProfile getUserProfile() {
        requireAuthorization();
        String command = EdriveConfig.getProperty(Const.ConfigKey.ACT_USER_PROFILE);
        String url = EdriveConfig.getProperty(Const.ConfigKey.API_URL) + command;
        getLogger().debug(command + " - url=" + url);
        return getRestTemplate().getForObject(URI.create(url), UserProfile.class);
    }

    @Override
    public UserProfile uploadUserHeadProtait(byte[] portrait) {
        return null;
    }
}
