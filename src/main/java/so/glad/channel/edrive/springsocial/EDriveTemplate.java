package so.glad.channel.edrive.springsocial;

import java.util.Date;

import com.google.common.collect.Lists;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.web.client.RestTemplate;
import so.glad.channel.edrive.api.EDrive;
import so.glad.channel.edrive.api.FileOperations;
import so.glad.channel.edrive.api.UploadOperations;
import so.glad.channel.edrive.api.UserOperations;
import so.glad.channel.edrive.utils.CodecUtil;

/**
 * @author palmtale
 * 2015/3/26.
 */
public class EDriveTemplate extends AbstractOAuth2ApiBinding implements EDrive {

    private String accessToken;

    private String appSecret;

    private UserOperations userOperations;

    private FileOperations fileOperations;

    private UploadOperations uploadOperations;

    public EDriveTemplate(String accessToken, String appSecret) {
        super(accessToken);
        this.accessToken = accessToken;
        this.appSecret = appSecret;
        initialize();
    }

    private void initialize() {
        // Wrap the request factory with a BufferingClientHttpRequestFactory so that the error handler can do repeat reads on the response.getBody()
        super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(getRestTemplate().getRequestFactory()));
        initSubApis();
    }

    private void initSubApis() {
        userOperations = new UserTemplate(createRestTemplate(), isAuthorized());
        fileOperations = new FileTemplate(createRestTemplate(), isAuthorized());
        uploadOperations = new UploadTemplate(createRestTemplate(), isAuthorized());
    }

    @Override
    public UserOperations userOperations() {
        return userOperations;
    }

    @Override
    public FileOperations fileOperations() {
        return fileOperations;
    }

    @Override
    public UploadOperations uploadOperations() {
        return uploadOperations;
    }

    private RestTemplate createRestTemplate(){
        RestTemplate restTemplate = getRestTemplate();
        restTemplate.setInterceptors(Lists.newArrayList((request, body, execution) -> {
            String date = new Date().toString();
            String source = "AccessToken=" + accessToken + "&Operate=" + request.getMethod()
                    + "&RequestURI=" +  request.getURI().getPath() + "&Date="+ date;
            String signature =  CodecUtil.hmacsha1(source, appSecret);

            request.getHeaders().set("Date", date);
            request.getHeaders().set("AccessToken", accessToken);
            request.getHeaders().set("Signature", signature);
            return execution.execute(request, body);
        }));
        restTemplate.getMessageConverters().add(new Jaxb2RootElementHttpMessageConverter());
        return restTemplate;
    }
}
