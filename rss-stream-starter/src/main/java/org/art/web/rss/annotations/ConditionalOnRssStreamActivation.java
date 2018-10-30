package org.art.web.rss.annotations;

import org.art.web.rss.conditionals.OnRssStreamActivationConditional;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Conditional(OnRssStreamActivationConditional.class)
public @interface ConditionalOnRssStreamActivation {
}
