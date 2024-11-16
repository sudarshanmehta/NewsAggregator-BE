package com.ooad.newsaggregator.services;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class EndpointLogger implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(EndpointLogger.class);

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Registered API Endpoints:");
        handlerMapping.getHandlerMethods().forEach((requestMappingInfo, handlerMethod) -> {
            // Check if PatternsCondition is present and retrieve patterns if available
            String paths = requestMappingInfo.getPatternsCondition() != null
                    ? requestMappingInfo.getPatternsCondition().getPatterns().toString()
                    : "No path mapped";

            logger.info("Path: {} | Method: {}", paths, handlerMethod.getMethod().getName());
        });
    }
}

