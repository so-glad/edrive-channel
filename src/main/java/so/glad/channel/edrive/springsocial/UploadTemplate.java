package so.glad.channel.edrive.springsocial;

import org.springframework.web.client.RestTemplate;
import so.glad.channel.edrive.api.UploadOperations;

/**
 * Created by palmtale on 2015/3/26.
 */
public class UploadTemplate extends AbstractOperations implements UploadOperations {
    public UploadTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(isAuthorized);
        super.setRestTemplate(restTemplate);
    }
}
