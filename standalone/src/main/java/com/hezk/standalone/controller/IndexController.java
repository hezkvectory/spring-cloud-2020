package com.hezk.standalone.controller;

import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/")
@RestController
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @GetMapping({"/", "/index"})
    public String index() {

        String traceId = TraceContext.traceId();

        ActiveSpan.tag("my_tag", "my_value");
        ActiveSpan.error();
        ActiveSpan.error("Test-Error-Reason");

        ActiveSpan.error(new RuntimeException("Test-Error-Throwable"));
        ActiveSpan.info("Test-Info-Msg");
        ActiveSpan.debug("Test-debug-Msg");

        LOGGER.info("traceId:{}", traceId);
        LOGGER.trace("trace....");
        LOGGER.debug("debug....");
        LOGGER.info("info....");
        LOGGER.warn("warn....");
        LOGGER.error("error....");
        return "index";
    }
}
