package so.glad.channel.edrive.impl;

import java.util.Date;
import java.util.Map;

import so.glad.channel.edrive.Const;
import so.glad.channel.edrive.EdriveConfig;
import so.glad.channel.edrive.utils.CodecUtil;

import so.glad.channel.oauth2.AccessGrant;
import so.glad.channel.oauth2.GrantType;
import so.glad.channel.oauth2.OAuth2Operations;
import so.glad.channel.oauth2.OAuth2Parameters;
import so.glad.channel.oauth2.util.OAuthUtil;

import javax.annotation.Resource;

/**
 * @author Cartoon
 * on 2015/3/26.
 */
public class OAuth2Template extends AbstractOperations implements OAuth2Operations {

    public OAuth2Template(String appKey, String appSecret){
        super(appKey, appSecret);
    }

    private OAuthUtil oauthUtil;

    @Override
    public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
        String url = EdriveConfig.getProperty(Const.ConfigKey.CLOUD_URL) + EdriveConfig.getProperty(Const.ConfigKey.ACT_USER_AUTH);
        String appKey = this.getAuthorizeParameters().getAppKey();
        String appSecret = this.getAuthorizeParameters().getAppSecret();
        Long timestamp = dateUtil.now().getTime();

        String source = "appKey=" + appKey + "&timestamp=" + timestamp;
        getLogger().debug("buildAuthorizeUrl - source=" + source);

        String dstAppSignature =  CodecUtil.hmacsha1(source, appSecret);
        url = url + "?appKey=" + appKey + "&timestamp=" + timestamp + "&appSignature=" + dstAppSignature
                + "&responseType=" + (grantType.equals(GrantType.AUTHORIZATION_CODE)?"code":"token") + "&callbackUrl=" + parameters.getRedirectUrl();
        if(parameters.getDisplay() != null){
            url += "&display=" + parameters.getDisplay();
        }
        if(parameters.getState() != null){
            url += "&state=" + parameters.getState();
        }
        getLogger().debug("buildAuthorizeUrl() - url=" + url);
        return url;
    }

    @Override
    public AccessGrant refreshAccess(String grantType, Map<String, String> paramters) {
        if (this.getClient() == null) {
            throw new RuntimeException("There's no client to request the access token.");
        }
        StringBuilder url = new StringBuilder(EdriveConfig.getProperty(Const.ConfigKey.CLOUD_URL) + EdriveConfig.getProperty(Const.ConfigKey.ACT_USER_TOKEN));
        Date date = new Date();
        Long timestamp = date.getTime();
        String appKey = this.getAuthorizeParameters().getAppKey();
        String appSecret = this.getAuthorizeParameters().getAppSecret();
        String source = "appKey=" + appKey + "&timestamp=" + timestamp;
        getLogger().debug("refreshAccess() - source=" + source);
        String dstAppSignature =  CodecUtil.hmacsha1(source, appSecret);
        url.append("?appKey=").append(appKey)
                .append("&timestamp=").append(timestamp)
                .append("&appSignature=").append(dstAppSignature)
                .append("&grantType=").append(grantType);
        for(Map.Entry<String,String> map:paramters.entrySet()){
            url.append("&").append(map.getKey()).append( "=").append(map.getValue());
        }
        getLogger().debug("refreshAccess() - url=" + url);
        return oauthUtil.fromJson(getClient().get(url.toString()));
    }

    @Resource
    public void setOauthUtil(OAuthUtil oauthUtil){
        this.oauthUtil = oauthUtil;
    }
}
