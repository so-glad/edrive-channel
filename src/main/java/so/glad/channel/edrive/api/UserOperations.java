package so.glad.channel.edrive.api;

import so.glad.channel.edrive.entity.UserInfo;
import so.glad.channel.edrive.entity.UserProfile;

/**
 * Created by palmtale on 2015/3/26.
 */
public interface UserOperations {

    UserInfo getUserInfo();

    UserProfile getUserProfile();

    UserProfile uploadUserHeadProtait(byte[] portrait);
}
