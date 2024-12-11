package org.segura.auditserviceproducer.config;
/*
 * Created by Daniel - 18/11/2024 (20:06)
 */

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/***
 *Sets up beans for HttpServletRequest and HttpServletResponse, creating fresh instances for each request
 */

@Configuration
public class WebConfig {
    /*

    @Bean
    @RequestScope
    @Qualifier("customHttpServletRequest")
    public HttpServletRequest httpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    @Bean
    @RequestScope
    @Qualifier("customHttpServletResponse")
    public HttpServletResponse httpServletResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }

     */
}
