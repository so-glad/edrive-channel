package so.glad.channel.edrive.springsocial;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import so.glad.channel.edrive.Const;
import so.glad.channel.edrive.api.EDrive;

/**
 * Created by palmtale on 2015/3/26.
 */
public class EDriveConnectionFactory extends OAuth2ConnectionFactory<EDrive> {
    /**
     * Creates a EdriveConnectionFactory for the given application ID, secret.
     * @param appId The application's App ID as assigned by Edrive
     * @param appSecret The application's App Secret as assigned by Edrive
     */
    public EDriveConnectionFactory(String appId, String appSecret) {
        super(Const.CHANNEL_ALIAS_EDRIVE, new EDriveServiceProvider(appId, appSecret), new EDriveAdapter());
    }

}