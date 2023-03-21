package blogbuddy.kakaosearch;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class KakaoClientInterceptor implements RequestInterceptor {
    @Value("${client.kakao.app-key}")
    private  String appKey;
    @Override
    public void apply(final RequestTemplate template) {
        template.header("Authorization", appKey);
    }
}
