package com.grupocordillera.ms_order;

import io.sentry.Sentry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsOrderApplication {

    public static void main(String[] args) {

        Sentry.init(options -> {
            options.setDsn(System.getenv("SENTRY_DSN"));
        });

        SpringApplication.run(MsOrderApplication.class, args);
    }
}