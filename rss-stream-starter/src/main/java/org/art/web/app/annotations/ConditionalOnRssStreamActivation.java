package org.art.web.app.annotations;

import org.art.web.app.conditionals.OnRssStreamActivationConditional;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Conditional(OnRssStreamActivationConditional.class)
public @interface ConditionalOnRssStreamActivation {
}
