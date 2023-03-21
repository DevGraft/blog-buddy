package blogbuddy.naversearch.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {
    /**
     * JSON 직렬화/역직렬화를 위한 Bean.
     * - timestamp 직렬화 제외
     * - JSON 포맷과 다른 형태라도 실패로 넘어가지 않도록 설정
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new CustomDateModule())
                .registerModule(new JsonComponentModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }


}
