package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.BlogPostFindRequest;
import blogbuddy.searchengine.domain.BlogPostFindService;
import blogbuddy.support.advice.exception.RequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DisplayName("블로그 검색 Service")
class BlogSearchServiceTest {
    @InjectMocks
    private BlogSearchService blogSearchService;
    @Mock
    private BlogPostFindService mockBlogPostFindService;
    @Captor
    private ArgumentCaptor<BlogPostFindRequest> blogPostFindRequestArgumentCaptor;

    @DisplayName("요청 값 [keyword]가 비어있을 경우 예외처리가 발생해야합니다.")
    @Test
    void searchPost_keywordValidationCheck() {
        final String givenKeyword = "";

        final RequestException exception = assertThrows(RequestException.class, () -> blogSearchService.searchPost(givenKeyword));

        assertThat(exception).isNotNull();
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getMessage()).isEqualTo("keyword 입력은 공백일 수 없습니다.");
    }

    @DisplayName("블로그 글 검색 정보 조회를 요청합니다.")
    @Test
    void searchPost_callFindBlogToBlogPostFindService() {
        final String givenKeyword = "kakaoLanding";

        blogSearchService.searchPost(givenKeyword);

        Mockito.verify(mockBlogPostFindService, Mockito.times(1))
                .findBlog(blogPostFindRequestArgumentCaptor.capture());
        Assertions.assertThat(blogPostFindRequestArgumentCaptor.getValue().getQuery()).isEqualTo(givenKeyword);
    }
}