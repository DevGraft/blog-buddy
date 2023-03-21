package blogbuddy.naversearch;

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
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

@Configuration
class NaverObjectMapperConfig {
    @Bean(name = "naverObjectMapper")
    public ObjectMapper objectMapper() {
        final SimpleModule localDateModule = new SimpleModule();
        localDateModule.addDeserializer(LocalDate.class, new LocalDateJsonDeserializer());
        return new ObjectMapper()
                .registerModule(localDateModule)
                .registerModule(new JsonComponentModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    private static class LocalDateJsonDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return LocalDate.parse(p.getValueAsString(), BASIC_ISO_DATE);
        }
    }
}

