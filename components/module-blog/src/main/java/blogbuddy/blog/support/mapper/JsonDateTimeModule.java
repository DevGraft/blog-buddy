package blogbuddy.blog.support.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

class JsonDateTimeModule extends SimpleModule {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT, Locale.KOREAN);
    JsonDateTimeModule() {
        this.addSerializer(LocalDateTime.class, new LocalDateTimeJsonSerializer());

    }
    private static class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(DATE_TIME_FORMATTER.format(value));
        }
    }
}
