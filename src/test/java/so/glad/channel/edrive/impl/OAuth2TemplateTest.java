package so.glad.channel.edrive.impl;

import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import so.glad.channel.edrive.Const;
import so.glad.channel.edrive.utils.CodecUtil;
import so.glad.channel.edrive.utils.DateUtil;
import so.glad.channel.oauth2.AccessGrant;
import so.glad.channel.oauth2.GrantType;
import so.glad.channel.oauth2.OAuth2Operations;
import so.glad.channel.oauth2.OAuth2Parameters;
import so.glad.client.SimpleClientImpl;

import java.util.Date;
import java.util.Map;

/**
 * Created by Cartoon on 2015/3/27.
 */
public class OAuth2TemplateTest {

    private OAuth2Operations oAuth2Operations;

    private String appKey = "600080118";

    private String appSecret = "21184e4f8575ec23b909fde70f6f9ce0";

    private Date date = new Date();

    private DateUtil dateUtil = new DateUtil() {
        @Override
        public String getFormatDateString(Date date, String format) {
            return null;
        }

        @Override
        public Date getDateFromFormatString(String str, String format) {
            return null;
        }

        @Override
        public Date now() {
            return date;
        }
    };

    @Before
    public void prepare(){
        OAuth2Template oAuth2Template = new OAuth2Template(appKey, appSecret);
        oAuth2Template.setDateUtil(dateUtil);
        oAuth2Template.setAccessToken("E59D69237620D8BC61EF69615E704B49");
        oAuth2Template.setClient(new SimpleClientImpl());
        oAuth2Operations = oAuth2Template;
    }

    @Test
    public void testBuildAuthorizeUrl(){
        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        oAuth2Parameters.setRedirectUrl("http:www.glad.so");
        String source = "appKey=" + appKey + "&timestamp=" + date.getTime();
        String url = oAuth2Operations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
        Assert.assertEquals("http://cloud.189.cn/open/oauth2/authorize.action?appKey=" + appKey + "&timestamp=" + date.getTime()
                + "&appSignature=" + CodecUtil.hmacsha1(source, appSecret) + "&responseType=code&callbackUrl=http:www.glad.so", url);
        System.out.println(url);
    }

    @Test
    public void testCodeRefreshAccess(){
        Map<String, String> map = Maps.newHashMap();
        map.put("code", "03745316");
        AccessGrant accessGrant = oAuth2Operations.refreshAccess(Const.GrantType.AUTHORIZATION_CODE, map);
        accessGrant.getAccessToken();
        accessGrant.getExpireTime();
        map.remove("code");
        map.put("refreshToken", "REFRESHTOKEN");
        accessGrant = oAuth2Operations.refreshAccess(Const.GrantType.REFRESH_TOKEN, map);
    }
}
