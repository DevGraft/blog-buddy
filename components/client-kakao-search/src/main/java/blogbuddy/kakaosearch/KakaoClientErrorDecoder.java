package blogbuddy.kakaosearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
class KakaoClientErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper;

    public KakaoClientErrorDecoder(@Qualifier("kakaoObjectMapper") final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(final String methodKey, final Response response) {
        try {
            final byte[] bytes = response.body().asInputStream().readAllBytes();
            final Map<String, String> data = objectMapper.readValue(bytes, Map.class);
            final String errorType = data.get("errorType");
            final String message = data.get("message");
            return KakaoClientException.mapped(response.status(), errorType, message);
        } catch (IOException e) {
            return KakaoClientException.mapped(HttpStatus.INTERNAL_SERVER_ERROR.value(), "", e.getMessage());
        }
    }
}
