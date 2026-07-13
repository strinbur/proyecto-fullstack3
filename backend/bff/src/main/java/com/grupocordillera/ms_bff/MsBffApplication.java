package com.grupocordillera.ms_bff;

import io.sentry.Sentry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsBffApplication {

	public static void main(String[] args) {

		Sentry.init(options -> {
			options.setDsn(System.getenv("SENTRY_DSN"));
		});

		SpringApplication.run(MsBffApplication.class, args);
	}
}