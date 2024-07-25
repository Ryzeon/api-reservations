package me.ryzeon.reservations.conector;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.AllArgsConstructor;
import me.ryzeon.reservations.conector.configuration.EndpointConfiguration;
import me.ryzeon.reservations.conector.configuration.HostConfiguration;
import me.ryzeon.reservations.conector.configuration.HttpConnectorConfiguration;
import me.ryzeon.reservations.conector.response.CityDto;
import me.ryzeon.reservations.enums.ApiException;
import me.ryzeon.reservations.exception.ReservationException;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.ConnectionObserver;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;


@Component
@AllArgsConstructor
public class CatalogConnector {

    private HttpConnectorConfiguration configuration;

    private final String HOST = "api-catalog";

    private final String ENDPOINT = "get-city";

    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CatalogConnector.class);

    @CircuitBreaker(name = "api-catalog", fallbackMethod = "fallbackGetCity")
    public CityDto getCity(String code) {
        HostConfiguration hostConfiguration = configuration.getHosts().get(HOST);
        EndpointConfiguration endpointConfiguration = hostConfiguration.getEndpoints().get(ENDPOINT);

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Math.toIntExact(endpointConfiguration.getConnectionTimeout()))
                .observe((conn, state) -> {
                    if (state == ConnectionObserver.State.CONFIGURED) {
                        conn.addHandlerLast(new ReadTimeoutHandler(endpointConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS));
                        conn.addHandlerLast(new WriteTimeoutHandler(endpointConfiguration.getWriteTimeout(), TimeUnit.MILLISECONDS));
                    }
                });


        WebClient client = WebClient.builder()
                .baseUrl("https://" + hostConfiguration.getHost() + ":" + hostConfiguration.getPort() + endpointConfiguration.getUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();


        return client.get()
                .uri(urlEncoder -> urlEncoder.build(code))
                .retrieve()
                .bodyToMono(CityDto.class)
                .share()
                .block();
    }

    /**
     * Fallback method for when the circuit breaker is open and the primary call to get city information is not permitted.
     * This method logs the event and returns a default {@link CityDto} instance with the provided code and null values for other fields.
     *
     * @param code The city code for which information was requested.
     * @param ex The {@link CallNotPermittedException} exception indicating the circuit breaker is open.
     * @return A {@link CityDto} instance with the provided code and null values for other fields.
     */
    public CityDto fallbackGetCity(String code, CallNotPermittedException ex) {
        LOGGER.info("Circuit breaker is open for api-catalog, but fallback is not ready");
        return new CityDto(null, code, null);
    }

    /**
     * Fallback method for handling exceptions other than {@link CallNotPermittedException} when calling the api-catalog.
     * This method logs the error and throws a {@link ReservationException} indicating a validation error.
     *
     * @param code The city code for which information was requested.
     * @param ex The exception that occurred during the api call.
     * @throws ReservationException indicating a validation error.
     */
    public CityDto fallbackGetCity(String code, Exception ex) {
        LOGGER.error("Error while calling api-catalog", ex);
        throw new ReservationException(ApiException.VALIDATION_ERROR);
    }
}
