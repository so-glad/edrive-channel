package so.glad.channel.edrive.springsocial;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.*;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.support.FormMapHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import so.glad.channel.edrive.Const;
import so.glad.channel.edrive.EdriveConfig;
import so.glad.channel.edrive.utils.CodecUtil;
import so.glad.channel.edrive.utils.DateUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Palmtale
 * 2015/3/26.
 */
public class EDriveOAuth2Template implements OAuth2Operations {

    private final String appKey;

    private final String appSecret;

    private final String accessTokenUrl;

    private final String authorizeUrl;

    private String authenticateUrl;

    private RestTemplate restTemplate;

    private boolean useParametersForClientAuthentication = true;

    private DateUtil dateUtil = DateUtil.DEFAULT_IMPL;

    public EDriveOAuth2Template(String appKey, String appSecret) {
        Assert.notNull(appKey, "The appKey property cannot be null");
        Assert.notNull(appSecret, "The appSecret property cannot be null");
        this.appKey = appKey;
        this.appSecret = appSecret;
        String clientInfo = "?appKey=" + formEncode(appKey);
        this.authorizeUrl = EdriveConfig.getProperty(Const.ConfigKey.CLOUD_URL) + EdriveConfig.getProperty(Const.ConfigKey.ACT_USER_AUTH) + clientInfo;
        if (authenticateUrl != null) {
            this.authenticateUrl = authenticateUrl + clientInfo;
        } else {
            this.authenticateUrl = null;
        }
        this.accessTokenUrl = EdriveConfig.getProperty(Const.ConfigKey.CLOUD_URL) + EdriveConfig.getProperty(Const.ConfigKey.ACT_USER_TOKEN);
    }

    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return buildAuthUrl(authorizeUrl, GrantType.AUTHORIZATION_CODE, parameters);
    }

    @Override
    public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
        return buildAuthUrl(authorizeUrl, grantType, parameters);
    }

    @Override
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        return authenticateUrl != null ? buildAuthUrl(authenticateUrl, GrantType.AUTHORIZATION_CODE, parameters) : buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, parameters);
    }

    @Override
    public String buildAuthenticateUrl(GrantType grantType, OAuth2Parameters parameters) {
        return authenticateUrl != null ? buildAuthUrl(authenticateUrl, grantType, parameters) : buildAuthorizeUrl(grantType, parameters);
    }

    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        if (useParametersForClientAuthentication) {
            params.set("appKey", appKey);
            params.set("appSecret", appSecret);
        }
        params.set("code", authorizationCode);
        params.set("callbackUrl", redirectUri);
        params.set("grant_type", "authorization_code");
        if (additionalParameters != null) {
            params.putAll(additionalParameters);
        }
        return postForAccessGrant(accessTokenUrl, params);
    }

    @Override
    public AccessGrant exchangeCredentialsForAccess(String username, String password, MultiValueMap<String, String> additionalParameters) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        if (useParametersForClientAuthentication) {
            params.set("appKey", appKey);
            params.set("appSecret", appSecret);
        }
        params.set("username", username);
        params.set("password", password);
        params.set("grant_type", "password");
        if (additionalParameters != null) {
            params.putAll(additionalParameters);
        }
        return postForAccessGrant(accessTokenUrl, params);
    }

    @Override
    public AccessGrant refreshAccess(String refreshToken, String scope, MultiValueMap<String, String> additionalParameters) {
        return null;
    }

    @Override
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {
        return null;
    }

    @Override
    public AccessGrant authenticateClient() {
        return null;
    }

    @Override
    public AccessGrant authenticateClient(String scope) {
        return null;
    }

    private String formEncode(String data) {
        try {
            return URLEncoder.encode(data, Const.DEFAULT_ENCODING);
        }
        catch (UnsupportedEncodingException ex) {
            // should not happen, UTF-8 is always supported
            throw new IllegalStateException(ex);
        }
    }

    private String buildAuthUrl(String baseAuthUrl, GrantType grantType, OAuth2Parameters parameters) {
        StringBuilder authUrl = new StringBuilder(baseAuthUrl);
        Long timestamp = dateUtil.now().getTime();
        String source = "appKey=" + appKey + "&timestamp=" + timestamp;
        String signature =  CodecUtil.hmacsha1(source, appSecret);
        authUrl.append("&timestamp=").append(timestamp).append("&appSignature=").append(signature);
        if (grantType == GrantType.AUTHORIZATION_CODE) {
            authUrl.append('&').append("responseType").append('=').append("code");
        } else if (grantType == GrantType.IMPLICIT_GRANT) {
            authUrl.append('&').append("responseType").append('=').append("token");
        }
        for (Iterator<Map.Entry<String, List<String>>> additionalParams = parameters.entrySet().iterator(); additionalParams.hasNext();) {
            Map.Entry<String, List<String>> param = additionalParams.next();
            String name = formEncode(param.getKey());
            for (Iterator<String> values = param.getValue().iterator(); values.hasNext();) {
                authUrl.append('&').append(name).append('=').append(formEncode(values.next()));
            }
        }
        return authUrl.toString();
    }

    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        return extractAccessGrant(getRestTemplate().postForObject(accessTokenUrl, parameters, Map.class));
    }

    private AccessGrant extractAccessGrant(Map<String, Object> result) {
        return createAccessGrant((String) result.get("access_token"), (String) result.get("scope"), (String) result.get("refresh_token"), getIntegerValue(result, "expires_in"), result);
    }

    protected AccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, Map<String, Object> response) {
        return new AccessGrant(accessToken, scope, refreshToken, expiresIn);
    }

    protected RestTemplate getRestTemplate() {
        // Lazily create RestTemplate to make sure all parameters have had a chance to be set.
        // Can't do this InitializingBean.afterPropertiesSet() because it will often be created directly and not as a bean.
        if (restTemplate == null) {
            restTemplate = createRestTemplate();
        }
        return restTemplate;
    }

    protected RestTemplate createRestTemplate() {
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactorySelector.getRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(2);
        converters.add(new FormHttpMessageConverter());
        converters.add(new FormMapHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        if (!useParametersForClientAuthentication) {
            List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
            if (interceptors == null) {   // defensively initialize list if it is null. (See SOCIAL-430)
                interceptors = new ArrayList<ClientHttpRequestInterceptor>();
                restTemplate.setInterceptors(interceptors);
            }
        }
        return restTemplate;
    }

    private Long getIntegerValue(Map<String, Object> map, String key) {
        try {
            return Long.valueOf(String.valueOf(map.get(key))); // normalize to String before creating integer value;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

