package blogbuddy.kakaosearch;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoInterceptor implements RequestInterceptor {
    private final String appKey;
    public KakaoInterceptor(@Value("${client.kakao.app-key}") final String appKey) {
        this.appKey = appKey;
    }


    @Override
    public void apply(final RequestTemplate template) {
        template.header("Authorization", appKey);
    }
}
