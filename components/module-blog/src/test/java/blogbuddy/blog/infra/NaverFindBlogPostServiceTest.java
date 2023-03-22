package blogbuddy.blog.infra;

import blogbuddy.blog.domain.FindBlogPostRequest;
import blogbuddy.blog.support.advice.exception.RequestException;
import blogbuddy.naversearch.NaverClient;
import blogbuddy.naversearch.NaverClientException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@DisplayName("네이버 by 블로그 포스트 조회 서비스")
class NaverFindBlogPostServiceTest {
    @InjectMocks
    private NaverFindBlogPostService findBlogPostService;
    @Mock
    private NaverClient mockNaverClient;
    @Captor
    private ArgumentCaptor<String> queryCaptor;
    @Captor
    private ArgumentCaptor<Integer> pageCaptor;
    @Captor
    private ArgumentCaptor<Integer> sizeCaptor;
    @Captor
    private ArgumentCaptor<String> sortCaptor;

    @DisplayName("전달받은 param을 NaverClient에 전달합니다.")
    @Test
    void findBlog_passesParamToNaverClient() {
        final FindBlogPostRequest givenRequest = FindBlogPostRequest.mapped("givenQuery", 1, 10, "recency");

        try {
            findBlogPostService.findBlog(givenRequest);
        } catch (Throwable ignored) {
        }

        Mockito.verify(mockNaverClient, Mockito.times(1))
                .searchBlog(queryCaptor.capture(), sizeCaptor.capture(), pageCaptor.capture(), sortCaptor.capture());

        assertThat(queryCaptor.getValue()).isEqualTo(givenRequest.getQuery());
        assertThat(pageCaptor.getValue()).isEqualTo(givenRequest.getPage());
        assertThat(sizeCaptor.getValue()).isEqualTo(givenRequest.getSize());
        assertThat(sortCaptor.getValue()).isEqualTo("date");
    }

    @DisplayName("NaverClient에서 에러가 발생 시 예외처리합니다.")
    @Test
    void findBlog_throwException() {
        final FindBlogPostRequest givenRequest = FindBlogPostRequest.mapped("givenQuery", 1, 10, "recency");
        BDDMockito.given(mockNaverClient.searchBlog(any(), any(), any(), any())).willThrow(NaverClientException.mapped(HttpStatus.BAD_REQUEST.value(), "", ""));

        RequestException exception = Assertions.assertThrows(RequestException.class, () -> findBlogPostService.findBlog(givenRequest));

        assertThat(exception).isNotNull();
    }
}
