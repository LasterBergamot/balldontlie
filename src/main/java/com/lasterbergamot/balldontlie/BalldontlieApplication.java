package com.lasterbergamot.balldontlie;

import com.lasterbergamot.balldontlie.domain.DataImporter;
import com.lasterbergamot.balldontlie.domain.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BalldontlieApplication implements CommandLineRunner {

	private final List<DataImporter> dataImporters;
	private final AppConfig appConfig;

	public static void main(String[] args) {
		SpringApplication.run(BalldontlieApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!appConfig.isSkipImport()) {
			dataImporters.forEach(DataImporter::doImport);
		}
	}
}
