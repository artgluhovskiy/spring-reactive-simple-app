package org.art.web.rss.conditionals;

import org.art.web.rss.configuration.ConfigConstants;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

public class OnRssStreamActivationConditional extends AllNestedConditions {

    public OnRssStreamActivationConditional() {
        super(ConfigurationPhase.REGISTER_BEAN);
    }

    @ConditionalOnProperty(
            name = "rss-streaming-service.rss-service-mode",
            havingValue = ConfigConstants.RSS_SERVICE_MODE
    )
    public static class OnEnvProperty {}
}
