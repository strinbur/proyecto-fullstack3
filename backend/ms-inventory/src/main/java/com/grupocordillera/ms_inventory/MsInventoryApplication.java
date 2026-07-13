package com.grupocordillera.ms_inventory;

import io.sentry.Sentry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsInventoryApplication {

	public static void main(String[] args) {

		Sentry.init(options -> {
			options.setDsn(System.getenv("SENTRY_DSN"));
		});

		SpringApplication.run(MsInventoryApplication.class, args);
	}

}