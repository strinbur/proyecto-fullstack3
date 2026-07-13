package com.grupocordillera.ms_cart.glitchtip;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class SentryLifecycle {

    private final GlitchTipErrorReporter errorReporter;

    public SentryLifecycle(GlitchTipErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }

    @PreDestroy
    public void onShutdown() {
        errorReporter.flush();
    }
}