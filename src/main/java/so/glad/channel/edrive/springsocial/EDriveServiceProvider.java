package so.glad.channel.edrive.springsocial;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import so.glad.channel.edrive.api.EDrive;

/**
 * Created by palmtale on 2015/3/26.
 */
public class EDriveServiceProvider extends AbstractOAuth2ServiceProvider<EDrive> {

    private String appSecret;

    /**
     * Creates a EDriveServiceProvider for the given application ID, secret.
     * @param appId The application's App ID as assigned by Edrive
     * @param appSecret The application's App Secret as assigned by Edrive
     *
     */
    public EDriveServiceProvider(String appId, String appSecret) {
        super(new EDriveOAuth2Template(appId, appSecret));
        this.appSecret = appSecret;
    }

    public EDrive getApi(String accessToken) {
        return new EDriveTemplate(accessToken, appSecret);
    }

}
