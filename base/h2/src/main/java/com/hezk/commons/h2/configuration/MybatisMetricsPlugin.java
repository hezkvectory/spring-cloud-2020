package com.hezk.commons.h2.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.boot.actuate.metrics.AutoTimer;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTags;

import java.util.Properties;

@Intercepts(
        value = {
                @Signature(type = Executor.class,
                        method = "update",
                        args = {MappedStatement.class, Object.class}),
                @Signature(type = Executor.class,
                        method = "query",
                        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class,
                                CacheKey.class, BoundSql.class}),
                @Signature(type = Executor.class,
                        method = "query",
                        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
        }
)
public class MybatisMetricsPlugin implements Interceptor {

    private final MeterRegistry registry;

    private final String metricName;

    private final AutoTimer autoTimer;

    public MybatisMetricsPlugin(MeterRegistry registry, String metricName, AutoTimer autoTimer) {
        this.registry = registry;
        this.metricName = metricName;
        this.autoTimer = autoTimer;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Timer.Sample timerSample = Timer.start(this.registry);
        final Object[] args = invocation.getArgs();
        if (args != null && args.length > 0) {
            final MappedStatement mappedStatement = (MappedStatement) args[0];
            if (mappedStatement != null) {
                final String methodName = mappedStatement.getId();
                try {
                    Object result = invocation.proceed();
                    record(timerSample, methodName, null);
                    return result;
                } catch (Throwable throwable) {
                    record(timerSample, methodName, throwable);
                    throw throwable;
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private void record(Timer.Sample sample, String methodName, Throwable exception) {
        if (this.autoTimer.isEnabled()) {
            Timer.Builder builder = this.autoTimer.builder(this.metricName);
            sample.stop(getTimer(builder, methodName, exception));
        }
    }

    private Timer getTimer(Timer.Builder builder, String method, Throwable exception) {
        Tags tags = Tags.of(Tag.of("method", method), WebMvcTags.exception(exception));
        return builder.tags(tags).register(this.registry);
    }
}