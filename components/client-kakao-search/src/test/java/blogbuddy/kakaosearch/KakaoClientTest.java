package blogbuddy.kakaosearch;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertThrows;
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = {
        KakaoClientConfig.class,
        KakaoObjectMapperConfig.class,
        KakaoClientInterceptor.class,
        KakaoClientErrorDecoder.class
})
class KakaoClientTest {
    @Autowired
    private KakaoClient kakaoClient;

    @DisplayName("kakao api 실제 호출 테스트")
    @Test
    void searchBlog_callTest() throws KakaoClientException {
        final KakaoSearchBlogResponse response = kakaoClient.searchBlog("테스트", null, 1, 10);
        Assertions.assertThat(response).isNotNull();
    }

    @DisplayName("kakao api를 잘못 요청할 시 에러를 캐치할 수 있어야합니다.")
    @Test
    void searchBlog_throwsKakaoSearchException() {
        final KakaoClientException exception = assertThrows(KakaoClientException.class, () ->
                kakaoClient.searchBlog("테스트", null, 1, 51));

        Assertions.assertThat(exception).isNotNull();
    }
}