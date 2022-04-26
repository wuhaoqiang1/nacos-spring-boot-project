package com.alibaba.boot.nacos.config.test;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.AbstractPropertyResolver;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;

/**
 * @author whq
 * @since 2022/4/26 22:01
 */
public class ValueAbstractPropertyResolver extends AbstractPropertyResolver implements EnvironmentAware {

    @Nullable
    private Environment environment;

    @Override
    protected String getPropertyAsRawString(String key) {
        return getProperty(key, String.class, true);
    }

    @Override
    public <T> T getProperty(String key, Class<T> targetType) {
        return getProperty(key, targetType, true);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Nullable
    protected <T> T getProperty(String key, Class<T> targetValueType, boolean resolveNestedPlaceholders) {

        Object value = this.environment.getProperty(key);
        if (value != null) {
            if (resolveNestedPlaceholders && value instanceof String) {
                value = resolveNestedPlaceholders((String) value);
            }
            return convertValueIfNecessary(value, targetValueType);
        }
        if (logger.isTraceEnabled()) {
            logger.trace("Could not find key '" + key + "' in any property source");
        }
        return null;
    }
}
