package blogbuddy.naversearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class NaverClientErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(final String methodKey, final Response response) {
        try {
            final byte[] bytes = response.body().asInputStream().readAllBytes();
            final Map<String, String> data = objectMapper.readValue(bytes, Map.class);
            final String errorCode = data.get("errorCode");
            final String errorMessage = data.get("errorMessage");
            return NaverClientException.mapped(response.status(), errorCode, errorMessage);
        } catch (IOException e) {
            return NaverClientException.mapped(HttpStatus.INTERNAL_SERVER_ERROR.value(), "", e.getMessage());
        }
    }
}
