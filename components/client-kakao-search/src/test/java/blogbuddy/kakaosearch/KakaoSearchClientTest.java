package blogbuddy.kakaosearch;

import blogbuddy.mapper.ObjectMapperConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import static org.junit.jupiter.api.Assertions.assertThrows;

class KakaoSearchClientTest {
    private KakaoSearchClient kakaoSearchClient;

    @BeforeEach
    void setUp() {
        final ObjectMapper objectMapper = new ObjectMapperConfig().objectMapper();

        kakaoSearchClient = Feign.builder()
                .client(new OkHttpClient())
                .requestInterceptor(new KakaoInterceptor("KakaoAK cfb90d2eb84ca8aa1e74e124d10d8e1f"))
                .decoder(new JacksonDecoder(objectMapper))
                .encoder(new JacksonEncoder())
                .errorDecoder(new KakaoSearchErrorDecoder(objectMapper))
                .contract(new SpringMvcContract())
                .target(KakaoSearchClient.class, "https://dapi.kakao.com");
    }

    @DisplayName("kakao api 실제 호출 테스트")
    @Test
    void searchBlog_callTest() throws KakaoSearchException {
        final SearchBlogResponse response = kakaoSearchClient.searchBlog("테스트", null, 1, 10);
        Assertions.assertThat(response).isNotNull();
    }

    @DisplayName("kakao api를 잘못 요청할 시 에러를 캐치할 수 있어야합니다.")
    @Test
    void searchBlog_throwsKakaoSearchException() {
        final KakaoSearchException exception = assertThrows(KakaoSearchException.class, () ->
                kakaoSearchClient.searchBlog("테스트", null, 1, 51));

        Assertions.assertThat(exception).isNotNull();
    }
}