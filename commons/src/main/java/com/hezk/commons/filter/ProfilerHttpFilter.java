package com.hezk.commons.filter;


import com.google.common.collect.Sets;
import com.hezk.commons.metrics.LatencyProfiler;
import com.hezk.commons.metrics.LatencyStat;
import com.hezk.commons.utils.HttpUtil;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import org.springframework.core.Ordered;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ProfilerHttpFilter implements Filter, Ordered {
    private static final Histogram REQUEST_LATENCY = Histogram.build()
            .name("requests_latency_seconds").help("Request latency in seconds.").register();

    private static final Gauge FTL_STAT = Gauge.build()
            .name("app_http_incoming_requests_ftl_gauge")
            .help("app_http_incoming_requests_ftl_gauge")
            .labelNames("url", "status")
            .register();

    private static final LatencyStat PROFILER_STAT = LatencyProfiler.Builder.build()
            .name("app_http_incoming_requests")
            .defineLabels("url", "status")
            .tag("http:incoming")
            .create();

    private static final Set<String> FTL_URL_SET = Sets.newConcurrentHashSet();

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest sRequest = (HttpServletRequest) request;
        HttpServletResponse sResponse = (HttpServletResponse) response;
        String url = HttpUtil.getPatternUrl(sRequest.getRequestURI());
        String metrics = sRequest.getMethod() + " " + url;
        long begin = System.currentTimeMillis();
        Histogram.Timer timer = REQUEST_LATENCY.startTimer();
        PROFILER_STAT.inc(metrics, "");
        boolean ftlFlag = false;
        if (!FTL_URL_SET.contains(metrics)) {
            FTL_URL_SET.add(metrics);
            ftlFlag = true;
        }
        try {
            chain.doFilter(request, response);
        } catch (IOException e) {
            PROFILER_STAT.error(metrics, IOException.class.getSimpleName());
            throw e;
        } catch (ServletException e) {
            PROFILER_STAT.error(metrics, ServletException.class.getSimpleName());
            throw e;
        } finally {
            PROFILER_STAT.dec(metrics, "");
            PROFILER_STAT.observe(metrics, String.valueOf(sResponse.getStatus()), System.currentTimeMillis() - begin, TimeUnit.MILLISECONDS);
            if (ftlFlag) {
                FTL_STAT.labels(metrics, String.valueOf(sResponse.getStatus())).set(System.currentTimeMillis() - begin);
            }
            timer.observeDuration();
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}