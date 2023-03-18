package blogbuddy.kakaosearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    public KakaoSearchClient kakaoSearchClient(
            @Autowired ObjectMapper objectMapper,
            @Autowired KakaoInterceptor kakaoInterceptor,
            @Autowired KakaoSearchErrorDecoder kakaoSearchErrorDecoder,
            @Value("${client.kakao.url}") String url) {
        return Feign.builder()
                .client(new OkHttpClient())
                .requestInterceptor(kakaoInterceptor)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder(objectMapper))
                .errorDecoder(kakaoSearchErrorDecoder)
                .contract(new SpringMvcContract())
                .target(KakaoSearchClient.class, url);
    }
}
