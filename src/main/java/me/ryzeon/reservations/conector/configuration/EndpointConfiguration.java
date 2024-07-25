package me.ryzeon.reservations.conector.configuration;

import lombok.Data;

@Data
public class EndpointConfiguration {

    private String url;

    private int readTimeout;

    private int writeTimeout;

    private int connectionTimeout;
}
