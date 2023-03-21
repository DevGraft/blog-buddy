package blogbuddy.naversearch;

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
class NaverClientConfig {
    @Bean
    public NaverClient naverSearchClient(
            ObjectMapper objectMapper,
            NaverClientInterceptor naverClientInterceptor,
            NaverClientErrorDecoder naverClientErrorDecoder,
            @Value("${client.naver.url}") String url) {
        return Feign.builder()
                .client(new OkHttpClient())
                .requestInterceptor(naverClientInterceptor)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder(objectMapper))
                .errorDecoder(naverClientErrorDecoder)
                .contract(new SpringMvcContract())
                .target(NaverClient.class, url);
    }
}
