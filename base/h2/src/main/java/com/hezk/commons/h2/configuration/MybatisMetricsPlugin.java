package com.hezk.commons.h2.configuration;

import io.micrometer.core.annotation.Timed;
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
import org.springframework.boot.actuate.autoconfigure.metrics.AutoTimeProperties;
import org.springframework.boot.actuate.metrics.AutoTimer;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTags;
import org.springframework.core.annotation.MergedAnnotationCollectors;
import org.springframework.core.annotation.MergedAnnotations;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

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

    public MybatisMetricsPlugin(MeterRegistry registry, String metricName) {
        this.registry = registry;
        this.metricName = metricName;
        this.autoTimer = new AutoTimeProperties();
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MybatisMetricsPlugin.TimingContext timingContext = startAndAttachTimingContext();
        final Object[] args = invocation.getArgs();
        if (args != null && args.length > 0) {
            final MappedStatement mappedStatement = (MappedStatement) args[0];
            if (mappedStatement != null) {
                final String methodName = mappedStatement.getId();
                try {
                    Object result = invocation.proceed();
                    record(timingContext, invocation.getMethod(), methodName, null);
                    return result;
                } catch (Throwable throwable) {
                    record(timingContext, invocation.getMethod(), methodName, throwable);
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

    private void record(MybatisMetricsPlugin.TimingContext timingContext, Method method, String methodName, Throwable exception) {
        Set<Timed> annotations = getTimedAnnotations(method);
        Timer.Sample timerSample = timingContext.getTimerSample();
        if (annotations.isEmpty()) {
            if (this.autoTimer.isEnabled()) {
                Timer.Builder builder = this.autoTimer.builder(this.metricName);
                timerSample.stop(getTimer(builder, methodName, exception));
            }
        } else {
            for (Timed annotation : annotations) {
                Timer.Builder builder = Timer.builder(annotation, this.metricName);
                timerSample.stop(getTimer(builder, methodName, exception));
            }
        }
    }

    private Set<Timed> getTimedAnnotations(Method method) {
        Set<Timed> methodAnnotations = findTimedAnnotations(method);
        if (!methodAnnotations.isEmpty()) {
            return methodAnnotations;
        }
        return findTimedAnnotations(method.getDeclaringClass());
    }

    private Set<Timed> findTimedAnnotations(AnnotatedElement element) {
        MergedAnnotations annotations = MergedAnnotations.from(element);
        if (!annotations.isPresent(Timed.class)) {
            return Collections.emptySet();
        }
        return annotations.stream(Timed.class).collect(MergedAnnotationCollectors.toAnnotationSet());
    }

    private Timer getTimer(Timer.Builder builder, String method, Throwable exception) {
        Tags tags = Tags.of(Tag.of("method", method), WebMvcTags.exception(exception));
        return builder.tags(tags).register(this.registry);
    }

    private MybatisMetricsPlugin.TimingContext startAndAttachTimingContext() {
        Timer.Sample timerSample = Timer.start(this.registry);
        MybatisMetricsPlugin.TimingContext timingContext = new MybatisMetricsPlugin.TimingContext(timerSample);
        return timingContext;
    }

    private static class TimingContext {

        private final Timer.Sample timerSample;

        TimingContext(Timer.Sample timerSample) {
            this.timerSample = timerSample;
        }

        Timer.Sample getTimerSample() {
            return this.timerSample;
        }
    }
}