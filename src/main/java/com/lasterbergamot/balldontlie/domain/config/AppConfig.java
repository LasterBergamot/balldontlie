package com.lasterbergamot.balldontlie.domain.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.lasterbergamot.balldontlie.util.Constants.CONFIG_PROP_APP_CONFIG;

@Configuration
@ConfigurationProperties(CONFIG_PROP_APP_CONFIG)
@Getter
@Setter
public class AppConfig {
    private boolean skipImport;
}
