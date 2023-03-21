package blogbuddy.naversearch.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

public class CustomDateModule extends SimpleModule {

    public CustomDateModule() {
        this.addDeserializer(LocalDate.class, new LocalDateJsonDeserializer());
    }

    private static class LocalDateJsonDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return LocalDate.parse(p.getValueAsString(), BASIC_ISO_DATE);
        }
    }
}
