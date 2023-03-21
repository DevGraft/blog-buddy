package blogbuddy.kakaosearch;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class KakaoObjectMapperConfig {
    @Bean(name = "kakaoObjectMapper")
    public ObjectMapper objectMapper() {
        final SimpleModule offsetDateTimeModule = new SimpleModule();
        offsetDateTimeModule.addDeserializer(OffsetDateTime.class, new OffsetDateJsonDeserializer());
        return new ObjectMapper()
                .registerModule(offsetDateTimeModule)
                .registerModule(new JsonComponentModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    private static final String OFFSET_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final DateTimeFormatter OFFSET_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(OFFSET_DATE_TIME_FORMAT, Locale.KOREAN);
    private static class OffsetDateJsonDeserializer extends JsonDeserializer<OffsetDateTime> {
        @Override
        public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return OffsetDateTime.parse(p.getValueAsString(), OFFSET_DATE_TIME_FORMATTER);
        }
    }

}
