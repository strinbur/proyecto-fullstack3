package com.grupocordillera.ms_data_aggregation.common.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeignConfigTest {

    @Test
    void requestInterceptor_shouldNotBeNull() {

        FeignConfig config = new FeignConfig();

        assertNotNull(config.requestInterceptor());
    }
}