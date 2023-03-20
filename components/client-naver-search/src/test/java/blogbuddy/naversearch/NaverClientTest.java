package blogbuddy.naversearch;

import blogbuddy.mapper.ObjectMapperConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = {
        ClientConfig.class,
        ObjectMapperConfig.class,
        NaverClientInterceptor.class,
        NaverClientErrorDecoder.class
})
class NaverClientTest {
    @Autowired
    private NaverClient naverClient;

    @DisplayName("클라이언트 API 요청 테스트")
    @Test
    void client_successAPIRequest() throws NaverClientException {
        final SearchBlogResponse pc = naverClient.searchBlog("PC", null, null, null);
        Assertions.assertThat(pc).isNotNull();
    }

    @DisplayName("부적절한 요청 시 클라이언트는 에러를 발생시킵니다.")
    @Test
    void client_throwNaverClientException() {
        final NaverClientException naverClientException = assertThrows(NaverClientException.class, () ->
                naverClient.searchBlog("PC", 11111, null, null));

        Assertions.assertThat(naverClientException).isNotNull();
    }
}