package so.glad.channel.edrive.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import so.glad.channel.edrive.AuthorizeParameters;
import so.glad.channel.edrive.Const;
import so.glad.channel.edrive.EdriveConfig;
import so.glad.channel.edrive.utils.CodecUtil;
import so.glad.channel.edrive.utils.DateUtil;
import so.glad.client.SimpleClient;
import so.glad.client.http.HttpConnectionContext;
import so.glad.client.http.HttpMethod;
import so.glad.client.http.URLWrapper;
import so.glad.serializer.StreamSerializer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Cartoon on 2015/3/26.
 */
public abstract class AbstractOperations {

    private Logger logger;

    private AuthorizeParameters authorizeParameters;

    private SimpleClient client;

    private StreamSerializer streamSerializer;

    protected DateUtil dateUtil = DateUtil.DEFAULT_IMPL;

    public AbstractOperations(String appKey, String appSecret){
        authorizeParameters = new AuthorizeParameters();
        authorizeParameters.setAppKey(appKey);
        authorizeParameters.setAppSecret(appSecret);
        logger = LoggerFactory.getLogger(this.getClass());
    }

    public void setAccessToken(String accessToken){
        authorizeParameters.setAccessToken(accessToken);
    }

    public void setClient(SimpleClient client){
        this.client = client;
    }

    protected AuthorizeParameters getAuthorizeParameters() {
        return authorizeParameters;
    }

    protected Logger getLogger() {
        return logger;
    }

    protected SimpleClient getClient(){
        return this.client;
    }

    private HttpConnectionContext generateHttpContext(String command, String httpMethod){
        String date = new Date().toString();
        String appSecret = this.getAuthorizeParameters().getAppSecret();
        String accessToken = this.getAuthorizeParameters().getAccessToken();
        String source = "AccessToken=" + accessToken + "&Operate=" + httpMethod + "&RequestURI=" + command + "&Date="+ date;
        getLogger().debug(command + " - source=" + source);
        String dstAppSignature =  CodecUtil.hmacsha1(source, appSecret);
        HttpConnectionContext httpConnectionContext = new HttpConnectionContext();
        httpConnectionContext.setHeaderProperties(new Properties());
        httpConnectionContext.getHeaderProperties().put("Date", date);
        httpConnectionContext.getHeaderProperties().put("AccessToken", accessToken);
        httpConnectionContext.getHeaderProperties().put("Signature", dstAppSignature);
        return httpConnectionContext;
    }

    protected <T>T operate(String command, String data, HttpMethod httpMethod, Class<T> responseType){
        String url = EdriveConfig.getProperty(Const.ConfigKey.API_URL) + command;
        getLogger().debug(command + " - url=" + url);
        HttpConnectionContext httpConnectionContext = generateHttpContext(command, httpMethod.toString());
        if(responseType == null && HttpMethod.PUT.equals(httpMethod)) {
            getClient().put(url, new ByteArrayInputStream(data.getBytes()), httpConnectionContext);
            return null;
        } else if (CharSequence.class.isAssignableFrom(responseType)){
            String result = httpMethod.equals(HttpMethod.POST) ? getClient().post(url, data, httpConnectionContext) : getClient().get(url + "?" + data, httpConnectionContext);
            result = result.substring(result.indexOf('>') + 4);
            result = result.substring(result.indexOf('>') + 1, result.indexOf("</"));
            return (T)result;
        } else {
            InputStream inputStream = httpMethod.equals(HttpMethod.POST) ? getClient().post(new URLWrapper(url, "default"),
                     new ByteArrayInputStream(data.getBytes()), httpConnectionContext)
                    : getClient().getAsStream(url + "?" + data, httpConnectionContext);
            return (T)streamSerializer.unmarshal(inputStream, Const.DEFAULT_ENCODING);
        }
    }

    public void setStreamSerializer(StreamSerializer streamSerializer) {
        this.streamSerializer = streamSerializer;
    }

    void setDateUtil(DateUtil dateUtil){
        this.dateUtil = dateUtil;
    }

}
