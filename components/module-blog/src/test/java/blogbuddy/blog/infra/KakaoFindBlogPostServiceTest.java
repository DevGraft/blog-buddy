package blogbuddy.blog.infra;

import blogbuddy.blog.domain.FindBlogPostRequest;
import blogbuddy.kakaosearch.KakaoClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
@DisplayName("카카오 by 블로그 포스트 조회 서비스")
class KakaoFindBlogPostServiceTest {
    @InjectMocks
    private KakaoFindBlogPostService findBlogPostService;
    @Mock
    private KakaoClient mockKakaoClient;
    @Captor
    private ArgumentCaptor<String> queryCaptor;
    @Captor
    private ArgumentCaptor<Integer> pageCaptor;
    @Captor
    private ArgumentCaptor<Integer> sizeCaptor;
    @Captor
    private ArgumentCaptor<String> sortCaptor;

    @DisplayName("전달받은 param을 kakaoClient에 전달합니다.")
    @Test
    void findBlog_passesParamToKakaoClient() {
        final FindBlogPostRequest givenRequest = FindBlogPostRequest.mapped("givenQuery", 1, 10, "recency");

        try {
            findBlogPostService.findBlog(givenRequest);
        } catch (Throwable ignored) {}

        Mockito.verify(mockKakaoClient, Mockito.times(givenRequest.getPage()))
                .searchBlog(queryCaptor.capture(), sortCaptor.capture(), pageCaptor.capture(), sizeCaptor.capture());

        assertThat(queryCaptor.getValue()).isEqualTo(givenRequest.getQuery());
        assertThat(pageCaptor.getValue()).isEqualTo(givenRequest.getPage());
        assertThat(sizeCaptor.getValue()).isEqualTo(givenRequest.getSize());
        assertThat(sortCaptor.getValue()).isEqualTo(givenRequest.getSort());
    }
}
