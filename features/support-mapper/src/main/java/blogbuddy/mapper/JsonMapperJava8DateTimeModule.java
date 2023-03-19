package blogbuddy.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class JsonMapperJava8DateTimeModule extends SimpleModule {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String OFFSET_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT, Locale.KOREAN);
    private static final DateTimeFormatter OFFSET_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(OFFSET_DATE_TIME_FORMAT, Locale.KOREAN);
    public JsonMapperJava8DateTimeModule() {
        /*LocalDateTime Format*/
        this.addSerializer(LocalDateTime.class, new LocalDateTimeJsonSerializer());
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeJsonDeserializer());
        /*OffsetDateTime Format*/
        this.addSerializer(OffsetDateTime.class, new OffsetDateTimeJsonSerializer());
        this.addDeserializer(OffsetDateTime.class, new OffsetDateJsonDeserializer());
    }
    private static class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(DATE_TIME_FORMATTER.format(value));
        }
    }
    private static class LocalDateTimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return LocalDateTime.parse(p.getValueAsString(), DATE_TIME_FORMATTER);
        }
    }
    private static class OffsetDateTimeJsonSerializer extends JsonSerializer<OffsetDateTime> {
        @Override
        public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(OFFSET_DATE_TIME_FORMATTER.format(value));
        }
    }
    private static class OffsetDateJsonDeserializer extends JsonDeserializer<OffsetDateTime> {
        @Override
        public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return OffsetDateTime.parse(p.getValueAsString(), OFFSET_DATE_TIME_FORMATTER);
        }
    }
}
