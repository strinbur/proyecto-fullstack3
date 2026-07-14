package com.grupocordillera.ms_bff.tracing;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignRequestIdInterceptor implements RequestInterceptor {

    private static final String HEADER_NAME = "X-Request-Id";

    @Override
    public void apply(RequestTemplate template) {
        template.header(HEADER_NAME, RequestIdContext.getOrUnknown());
    }
}