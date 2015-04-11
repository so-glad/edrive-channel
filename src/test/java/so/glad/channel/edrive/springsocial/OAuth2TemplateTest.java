package so.glad.channel.edrive.springsocial;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;

/**
 * Created by palmtale on 2015/3/29.
 */
public class OAuth2TemplateTest {

    private OAuth2Operations oAuth2Operations;

    @Before
    public void prepare(){
        oAuth2Operations = new EDriveOAuth2Template("600080118" , "21184e4f8575ec23b909fde70f6f9ce0");
    }

    @Test
    public void testBuildAuthorizeUrl(){
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri("http://www.glad.so");
        String url = oAuth2Operations.buildAuthenticateUrl(GrantType.IMPLICIT_GRANT, parameters);
        Assert.assertNotNull(url);
        System.out.println(url);
        System.out.println("http://cloud.189.cn/open/oauth2/authorize.action?appKey=600098230&timestamp=1427646111334&appSignature=015A2DEC32065FA3DB61F757058D97D9C6517961&responseType=code&callbackUrl=http:www.glad.so");
    }
}
