package com.lasterbergamot.balldontlie.domain.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("app-config")
@Getter
@Setter
public class AppConfig {

    private boolean skipImport;

}
