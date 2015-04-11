package so.glad.channel.edrive.springsocial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.web.client.RestTemplate;
import so.glad.channel.edrive.Const;

/**
 * Created by palmtale on 2015/3/26.
 */
public abstract class AbstractOperations {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final boolean isAuthorized;

    private RestTemplate restTemplate;

    public AbstractOperations(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    protected void requireAuthorization() {
        if (!isAuthorized) {
            throw new MissingAuthorizationException(Const.CHANNEL_ALIAS_EDRIVE);
        }
    }
    protected Logger getLogger(){
        return this.logger;
    }

    protected void setRestTemplate(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    protected RestTemplate getRestTemplate(){
        return restTemplate;
    }

}

