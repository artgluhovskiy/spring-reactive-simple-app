package org.art.web.rss.annotations;

import org.art.web.rss.configuration.conditionals.OnStreamingModeConditional;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Conditional annotation for "streaming" mode.
 */
@Retention(RetentionPolicy.RUNTIME)
@Conditional(OnStreamingModeConditional.class)
public @interface ConditionalOnStreamingMode {
}
