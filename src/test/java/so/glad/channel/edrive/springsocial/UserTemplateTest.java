package so.glad.channel.edrive.springsocial;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import so.glad.channel.edrive.api.UserOperations;
import so.glad.channel.edrive.entity.UserInfo;
import so.glad.channel.edrive.entity.UserProfile;


/**
 * @author  palmtale
 * 2015/3/29.
 */
public class UserTemplateTest {
    private UserOperations userOperations;
    @Before
    public void prepare(){
        userOperations = new EDriveServiceProvider("600098230", "559f25c2c1e7952cd37e69fb1eff0042")
                .getApi("E59D69237620D8BC61EF69615E704B49")
                .userOperations();
    }
    @Test
    public void testGetUserInfo(){
        UserInfo userInfo = userOperations.getUserInfo();
        Assert.assertNotNull(userInfo);
    }
    @Test
    public void testGetUserProfile(){
        UserProfile userInfo = userOperations.getUserProfile();
        Assert.assertNotNull(userInfo);
    }
}
