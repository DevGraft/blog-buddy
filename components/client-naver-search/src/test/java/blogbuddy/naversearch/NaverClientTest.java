package blogbuddy.naversearch;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(classes = {
        NaverClientConfig.class,
        NaverObjectMapperConfig.class,
        NaverClientInterceptor.class,
        NaverClientErrorDecoder.class
})
class NaverClientTest {
    @Autowired
    private NaverClient naverClient;

    @DisplayName("클라이언트 API 요청 테스트")
    @Test
    void client_successAPIRequest() throws NaverClientException {
        final NaverSearchBlogResponse pc = naverClient.searchBlog("PC", 1, 10, null);
        Assertions.assertThat(pc).isNotNull();
        Assertions.assertThat(pc.getTotal()).isNotNull();
        Assertions.assertThat(pc.getStart()).isNotNull();
        Assertions.assertThat(pc.getDisplay()).isNotNull();
        Assertions.assertThat(pc.getLastBuildDate()).isNotNull();
        Assertions.assertThat(pc.getItems()).isNotEmpty();
        Assertions.assertThat(pc.getItems().get(0).getTitle()).isNotNull();
        Assertions.assertThat(pc.getItems().get(0).getLink()).isNotNull();
        Assertions.assertThat(pc.getItems().get(0).getDescription()).isNotNull();
        Assertions.assertThat(pc.getItems().get(0).getBloggerName()).isNotNull();
        Assertions.assertThat(pc.getItems().get(0).getBloggerLink()).isNotNull();
        Assertions.assertThat(pc.getItems().get(0).getPostdate()).isNotNull();
    }

    @DisplayName("부적절한 요청 시 클라이언트는 에러를 발생시킵니다.")
    @Test
    void client_throwNaverClientException() {
        final NaverClientException naverClientException = assertThrows(NaverClientException.class, () ->
                naverClient.searchBlog("PC", 11111, null, null));

        Assertions.assertThat(naverClientException).isNotNull();
    }
}