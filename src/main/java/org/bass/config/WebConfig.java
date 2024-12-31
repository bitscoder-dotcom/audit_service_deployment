package org.bass.config;
/*
 * Created by Daniel - 18/11/2024 (20:06)
 */

import org.springframework.context.annotation.Configuration;

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
