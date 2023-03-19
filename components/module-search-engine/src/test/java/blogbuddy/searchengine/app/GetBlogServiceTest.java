package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.FindBlogPostDocument;
import blogbuddy.searchengine.domain.FindBlogPostMeta;
import blogbuddy.searchengine.domain.FindBlogPostRequest;
import blogbuddy.searchengine.domain.FindBlogPostResponse;
import blogbuddy.searchengine.domain.FindBlogPostService;
import blogbuddy.support.advice.exception.RequestException;
import org.assertj.core.api.Assertions;
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

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;

@ExtendWith(MockitoExtension.class)
@DisplayName("블로그 검색 Service")
class GetBlogServiceTest {
    @InjectMocks
    private GetBlogService getBlogService;
    @Mock
    private FindBlogPostService mockFindBlogPostService;
    @Captor
    private ArgumentCaptor<FindBlogPostRequest> blogPostFindRequestArgumentCaptor;

    @DisplayName("요청 값 [keyword]가 비어있을 경우 예외처리가 발생해야합니다.")
    @Test
    void searchPost_keywordValidationCheck() {
        final String givenKeyword = "";

        final RequestException exception = assertThrows(RequestException.class, () -> getBlogService.getBlog(givenKeyword, null, null));

        assertThat(exception).isNotNull();
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getMessage()).isEqualTo("keyword 입력은 공백일 수 없습니다.");
    }

    @DisplayName("블로그 글 검색 정보 조회를 요청합니다.")
    @Test
    void searchPost_callFindBlogToBlogPostFindService() {
        final String givenKeyword = "kakaoLanding";
        final Integer givenPage = 1;
        final Integer givenSize = 10;
        final FindBlogPostMeta givenMeta = new FindBlogPostMeta(1, 1, true);
        final FindBlogPostResponse givenResponse = new FindBlogPostResponse(givenMeta, List.of());
        BDDMockito.given(mockFindBlogPostService.findBlog(any())).willReturn(givenResponse);

        getBlogService.getBlog(givenKeyword, givenPage, givenSize);

        Mockito.verify(mockFindBlogPostService, Mockito.times(1))
                .findBlog(blogPostFindRequestArgumentCaptor.capture());
        Assertions.assertThat(blogPostFindRequestArgumentCaptor.getValue().getQuery()).isEqualTo(givenKeyword);
        Assertions.assertThat(blogPostFindRequestArgumentCaptor.getValue().getPage()).isEqualTo(givenPage);
        Assertions.assertThat(blogPostFindRequestArgumentCaptor.getValue().getSize()).isEqualTo(givenSize);
    }

    @DisplayName("블로그 글 검색의 결과는 반환됩니다.")
    @Test
    void searchPost_returnValue() {
        final String givenKeyword = "kakaoLanding";
        final Integer givenPage = 1;
        final Integer givenSize = 10;
        final FindBlogPostRequest givenRequest = FindBlogPostRequest.mapped(givenKeyword, givenPage, givenSize);
        final FindBlogPostMeta givenMeta = new FindBlogPostMeta(1, 1, true);
        final FindBlogPostDocument givenDocument = new FindBlogPostDocument("title", "contents-kakaoLanding", "url", "blogName","thumbnail", OffsetDateTime.now());
        final FindBlogPostResponse givenResponse = new FindBlogPostResponse(givenMeta, List.of(givenDocument));
        BDDMockito.given(mockFindBlogPostService.findBlog(refEq(givenRequest))).willReturn(givenResponse);

        final GetBlogResponse response = getBlogService.getBlog(givenKeyword, givenPage, givenSize);

        assertThat(response.meta()).isNotNull();
        assertThat(response.meta().totalCount()).isEqualTo(givenResponse.meta().totalCount());
        assertThat(response.meta().pageableCount()).isEqualTo(givenResponse.meta().pageableCount());
        assertThat(response.meta().isEnd()).isEqualTo(givenResponse.meta().isEnd());
        assertThat(response.documents()).isNotEmpty();
        assertThat(response.documents().get(0).title()).isEqualTo(givenDocument.title());
        assertThat(response.documents().get(0).contents()).isEqualTo(givenDocument.contents());
        assertThat(response.documents().get(0).url()).isEqualTo(givenDocument.url());
        assertThat(response.documents().get(0).blogName()).isEqualTo(givenDocument.blogName());
        assertThat(response.documents().get(0).thumbnail()).isEqualTo(givenDocument.thumbnail());
        assertThat(response.documents().get(0).datetime()).isEqualTo(givenDocument.datetime());
    }
}