package me.ryzeon.reservations.conector.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@ConfigurationProperties(prefix = "http-connector")
@Data
public class HttpConnectorConfiguration {

    private HashMap<String, HostConfiguration> hosts;

}