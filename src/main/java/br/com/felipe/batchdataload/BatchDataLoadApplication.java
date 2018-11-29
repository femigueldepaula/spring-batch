package br.com.felipe.batchdataload;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;

@SpringBootApplication(exclude = {BatchAutoConfiguration.class})
@EnableBatchProcessing
public class BatchDataLoadApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchDataLoadApplication.class, args);
	}

}
