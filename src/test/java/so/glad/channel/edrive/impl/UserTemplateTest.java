package so.glad.channel.edrive.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import so.glad.channel.edrive.api.UserOperations;
import so.glad.channel.edrive.entity.JibxStreamSerializer;
import so.glad.channel.edrive.entity.UserInfo;
import so.glad.channel.edrive.entity.UserProfile;
import so.glad.client.SimpleClientImpl;

/**
 * Created by Cartoon on 2015/3/28.
 */
public class UserTemplateTest {
    private UserOperations userOperations;

    private String appKey = "600098230";

    private String appSecret = "559f25c2c1e7952cd37e69fb1eff0042";

    @Before
    public void prepare(){
        UserTemplate userTemplate = new UserTemplate(appKey, appSecret);
        userTemplate.setAccessToken("E59D69237620D8BC61EF69615E704B49");
        userTemplate.setClient(new SimpleClientImpl());
        userTemplate.setStreamSerializer(new JibxStreamSerializer());
        userOperations = userTemplate;
    }
    @Test
    public void testGetUserInfo(){
        UserInfo user = userOperations.getUserInfo();
        Assert.assertNotNull(user);
    }

    @Test
    public void testGetUserExt(){
        UserProfile userProfile = userOperations.getUserProfile();
        Assert.assertNotNull(userProfile);
    }
}
