package takeoff.logistics_service.msa.hub.hubroute.infrastructure.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 16.
 */
@Configuration
@Slf4j
public class WebClientConfig {

    @Bean
    public WebClient webClient() {

        //응답 데이터가 기본 값이 256KB인데 큰 JSON 데이터를 받을 것을 고려해 확장(30MB)
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024*1024*30))
            .build();
        //로깅을 위한 설정 아래 설정으로 인해 본문 데이터도 로깅 가능
        exchangeStrategies
            .messageWriters().stream()
            .filter(LoggingCodecSupport.class::isInstance)
            .forEach(writer -> ((LoggingCodecSupport)writer).setEnableLoggingRequestDetails(true));

        return WebClient.builder()
            .clientConnector(
                new ReactorClientHttpConnector(
                    HttpClient
                        .create()
                        //서버와 연결을 2분을 기다리고 응답은 최대 3분까지 기다리는 로직
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 120_000)
                        .doOnConnected(conn ->
                                conn.addHandlerLast(new ReadTimeoutHandler(180))
                                    .addHandlerLast(new WriteTimeoutHandler(180))
                                )
                        )
                )
            .build();
    }
}
