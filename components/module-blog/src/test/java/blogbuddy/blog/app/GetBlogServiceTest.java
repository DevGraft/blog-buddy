package blogbuddy.blog.app;

import blogbuddy.blog.domain.BlogEvents;
import blogbuddy.blog.domain.FindBlogPostDocument;
import blogbuddy.blog.domain.FindBlogPostMeta;
import blogbuddy.blog.domain.FindBlogPostRequest;
import blogbuddy.blog.domain.FindBlogPostResponse;
import blogbuddy.blog.domain.FindBlogPostService;
import blogbuddy.blog.domain.LocalDateTimeProvider;
import blogbuddy.blog.domain.SearchBlogEvent;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("블로그 검색 Service")
class GetBlogServiceTest {
    @InjectMocks
    private SearchBlogService searchBlogService;
    @Mock
    private SearchBlogParamValidator mockParamValidator;
    @Mock
    private FindBlogPostService mockFindBlogPostService;
    @Mock
    private LocalDateTimeProvider mockLocalDateTimeProvider;
    @Mock
    private BlogEvents events;
    @Captor
    private ArgumentCaptor<FindBlogPostRequest> blogPostFindRequestArgumentCaptor;
    @Captor
    private ArgumentCaptor<SearchBlogEvent> blogEventArgumentCaptor;

    @DisplayName("요청 값이 정상적인지 확인을 위해 Validator에게 검사를 요청합니다.")
    @Test
    void searchPost_passesParamToValidator() {
        final String givenKeyword = "";
        final Integer givenPage = 1;
        final Integer givenSize = 50;
        try {
            searchBlogService.searchBlog(givenKeyword, givenPage, givenSize, null);
        } catch (Throwable ignore) {}

        final ArgumentCaptor<String> keywordCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Integer> pageCaptor = ArgumentCaptor.forClass(Integer.class);
        final ArgumentCaptor<Integer> sizeCaptor = ArgumentCaptor.forClass(Integer.class);

        Mockito.verify(mockParamValidator, Mockito.times(1)).validate(keywordCaptor.capture(), pageCaptor.capture(), sizeCaptor.capture());
        assertThat(keywordCaptor.getValue()).isNotNull();
        assertThat(keywordCaptor.getValue()).isEqualTo(givenKeyword);
        assertThat(pageCaptor.getValue()).isNotNull();
        assertThat(pageCaptor.getValue()).isEqualTo(givenPage);
        assertThat(sizeCaptor.getValue()).isNotNull();
        assertThat(sizeCaptor.getValue()).isEqualTo(givenSize);
    }

    @DisplayName("블로그 글 검색 정보 조회를 요청합니다.")
    @Test
    void searchPost_callFindBlogToBlogPostFindService() {
        final String givenKeyword = "kakaoLanding";
        final Integer givenPage = 1;
        final Integer givenSize = 10;
        final String givenSort = "recency";
        final FindBlogPostMeta givenMeta = new FindBlogPostMeta(1, 1, true);
        final FindBlogPostResponse givenResponse = new FindBlogPostResponse(givenMeta, List.of());
        BDDMockito.given(mockFindBlogPostService.findBlog(any())).willReturn(givenResponse);

        searchBlogService.searchBlog(givenKeyword, givenPage, givenSize, givenSort);

        Mockito.verify(mockFindBlogPostService, times(1))
                .findBlog(blogPostFindRequestArgumentCaptor.capture());
        Assertions.assertThat(blogPostFindRequestArgumentCaptor.getValue().getQuery()).isEqualTo(givenKeyword);
        Assertions.assertThat(blogPostFindRequestArgumentCaptor.getValue().getPage()).isEqualTo(givenPage);
        Assertions.assertThat(blogPostFindRequestArgumentCaptor.getValue().getSize()).isEqualTo(givenSize);
        Assertions.assertThat(blogPostFindRequestArgumentCaptor.getValue().getSort()).isEqualTo(givenSort);
    }

    @DisplayName("블로그 글 검색의 결과는 반환됩니다.")
    @Test
    void searchPost_returnValue() {
        final FindBlogPostMeta givenMeta = new FindBlogPostMeta(1, 1, true);
        final FindBlogPostDocument givenDocument = new FindBlogPostDocument("title", "contents-kakaoLanding", "url", "blogName","thumbnail", LocalDateTime.now());
        final FindBlogPostResponse givenResponse = new FindBlogPostResponse(givenMeta, List.of(givenDocument));
        BDDMockito.given(mockFindBlogPostService.findBlog(any())).willReturn(givenResponse);

        final SearchBlogResponse response = searchBlogService.searchBlog("kakaoLanding", 1, 10, "accuracy");

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
        assertThat(response.documents().get(0).postDate()).isEqualTo(givenDocument.datetime().toLocalDate());
    }

    @DisplayName("블로그 검색 성공 시 이벤트가 발생합니다.")
    @Test
    void getBlog_publishGetBlogEvent() {
        final FindBlogPostMeta givenMeta = new FindBlogPostMeta(1, 1, true);
        final FindBlogPostDocument givenDocument = new FindBlogPostDocument("title", "contents-kakaoLanding", "url", "blogName","thumbnail", LocalDateTime.now());
        final FindBlogPostResponse givenResponse = new FindBlogPostResponse(givenMeta, List.of(givenDocument));
        BDDMockito.given(mockFindBlogPostService.findBlog(any())).willReturn(givenResponse);
        final String givenKeyword = "kakaoLanding";
        final LocalDateTime givenRegisterDatetime = LocalDateTime.now();
        BDDMockito.given(mockLocalDateTimeProvider.now()).willReturn(givenRegisterDatetime);

        searchBlogService.searchBlog(givenKeyword, 1, 10, "accuracy");

        Mockito.verify(events, times(1)).raise(blogEventArgumentCaptor.capture());
        Assertions.assertThat(blogEventArgumentCaptor.getValue()).isNotNull();
        Assertions.assertThat(blogEventArgumentCaptor.getValue().getKeyword()).isEqualTo(givenKeyword);
        Assertions.assertThat(blogEventArgumentCaptor.getValue().getRegisterDatetime()).isEqualTo(givenRegisterDatetime);
    }
}