package blogbuddy.kakaosearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class KakaoClientConfig {
    @Bean
    public KakaoClient kakaoSearchClient(
            ObjectMapper objectMapper,
            KakaoClientInterceptor kakaoClientInterceptor,
            KakaoClientErrorDecoder kakaoClientErrorDecoder,
            @Value("${client.kakao.url}") String url) {
        return Feign.builder()
                .client(new OkHttpClient())
                .requestInterceptor(kakaoClientInterceptor)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder(objectMapper))
                .errorDecoder(kakaoClientErrorDecoder)
                .contract(new SpringMvcContract())
                .target(KakaoClient.class, url);
    }
}
