package com.grupocordillera.ms_cart.tracing;

public class RequestIdContext {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void set(String requestId) {
        CONTEXT.set(requestId);
    }

    public static String getOrUnknown() {
        String value = CONTEXT.get();
        return value != null ? value : "unknown";
    }

    public static void clear() {
        CONTEXT.remove();
    }
}