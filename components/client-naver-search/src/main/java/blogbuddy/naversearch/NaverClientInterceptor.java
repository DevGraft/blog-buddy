package blogbuddy.naversearch;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NaverClientInterceptor implements RequestInterceptor {
    @Value("${client.naver.client-id}")
    private String clientId;
    @Value("${client.naver.secret}")
    private String secret;

    @Override
    public void apply(final RequestTemplate template) {
        template.header("X-Naver-Client-Id", clientId);
        template.header("X-Naver-Client-Secret", secret);
    }
}