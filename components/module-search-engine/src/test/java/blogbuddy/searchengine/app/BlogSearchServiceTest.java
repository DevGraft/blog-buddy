package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.BlogPostFindItem;
import blogbuddy.searchengine.domain.BlogPostFindRequest;
import blogbuddy.searchengine.domain.BlogPostFindResponse;
import blogbuddy.searchengine.domain.BlogPostFindService;
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
        final BlogPostFindResponse givenResponse = new BlogPostFindResponse(0, 0, true, List.of());
        BDDMockito.given(mockBlogPostFindService.findBlog(any())).willReturn(givenResponse);

        blogSearchService.searchPost(givenKeyword);

        Mockito.verify(mockBlogPostFindService, Mockito.times(1))
                .findBlog(blogPostFindRequestArgumentCaptor.capture());
        Assertions.assertThat(blogPostFindRequestArgumentCaptor.getValue().getQuery()).isEqualTo(givenKeyword);
    }

    @DisplayName("블로그 글 검색의 결과는 반환됩니다.")
    @Test
    void searchPost_returnValue() {
        final String givenKeyword = "kakaoLanding";
        final BlogPostFindRequest givenRequest = BlogPostFindRequest.mapped(givenKeyword);
        final BlogPostFindItem givenItem = new BlogPostFindItem("title", "contents-kakaoLanding", "url", "blogName","thumbnail", OffsetDateTime.now());
        final BlogPostFindResponse givenResponse = new BlogPostFindResponse(1, 1, true, List.of(givenItem));
        BDDMockito.given(mockBlogPostFindService.findBlog(refEq(givenRequest))).willReturn(givenResponse);

        final SearchPostResponse response = blogSearchService.searchPost(givenKeyword);

        assertThat(response.totalCount()).isEqualTo(givenResponse.getTotalCount());
        assertThat(response.pageableCount()).isEqualTo(givenResponse.getPageableCount());
        assertThat(response.isEnd()).isEqualTo(givenResponse.isEnd());
        assertThat(response.documents()).isNotEmpty();
        assertThat(response.documents().get(0).title()).isEqualTo(givenItem.getTitle());
        assertThat(response.documents().get(0).contents()).isEqualTo(givenItem.getContents());
        assertThat(response.documents().get(0).url()).isEqualTo(givenItem.getUrl());
        assertThat(response.documents().get(0).blogName()).isEqualTo(givenItem.getBlogName());
        assertThat(response.documents().get(0).thumbnail()).isEqualTo(givenItem.getThumbnail());
        assertThat(response.documents().get(0).datetime()).isEqualTo(givenItem.getDatetime());
    }
}