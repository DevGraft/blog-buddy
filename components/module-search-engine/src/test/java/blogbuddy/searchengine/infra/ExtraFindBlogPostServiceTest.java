package blogbuddy.searchengine.infra;

import blogbuddy.kakaosearch.KakaoClient;
import blogbuddy.kakaosearch.KakaoClientException;
import blogbuddy.kakaosearch.SearchBlogDocument;
import blogbuddy.kakaosearch.SearchBlogMeta;
import blogbuddy.kakaosearch.SearchBlogResponse;
import blogbuddy.searchengine.domain.FindBlogPostRequest;
import blogbuddy.searchengine.domain.FindBlogPostResponse;
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

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@DisplayName("외부모듈 블로그 조회 기능")
class ExtraFindBlogPostServiceTest {
    @InjectMocks
    private ExtraFindBlogPostService extraBlogPostFindService;
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

    @DisplayName("kakao api 호출 실패 시 예외처리가 핸들링되어야합니다.")
    @Test
    void findBlog_catchAndThrowsRequestException() throws KakaoClientException {
        final String givenQuery = "givenQuery";
        final FindBlogPostRequest givenRequest = FindBlogPostRequest.mapped(givenQuery, null, null, null);
        final String givenErrorMessage = "message";
        final int givenStatus = 400;
        BDDMockito.given(mockKakaoClient.searchBlog(eq(givenQuery), any(), any(), any()))
                .willThrow(KakaoClientException.mapped(givenStatus, "errorType", givenErrorMessage));

        final RequestException exception = assertThrows(RequestException.class, () ->
                extraBlogPostFindService.findBlog(givenRequest));

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(givenErrorMessage);
        assertThat(exception.getStatus().value()).isEqualTo(givenStatus);
    }

    @DisplayName("전달받은 param을 kakaoClient에 전달합니다.")
    @Test
    void findBlog_passesParamToKakaoClient() throws KakaoClientException {
        final String givenQuery = "givenQuery";
        final int givenPage = 1;
        final int givenSize = 10;
        final String givenSort = "recency";
        final FindBlogPostRequest givenRequest = FindBlogPostRequest.mapped(givenQuery, givenPage, givenSize, givenSort);

        try {
            extraBlogPostFindService.findBlog(givenRequest);
        } catch (Throwable ignored) {}

        Mockito.verify(mockKakaoClient, Mockito.times(givenPage))
                .searchBlog(queryCaptor.capture(), sortCaptor.capture(), pageCaptor.capture(), sizeCaptor.capture());

        Assertions.assertThat(queryCaptor.getValue()).isEqualTo(givenQuery);
        Assertions.assertThat(pageCaptor.getValue()).isEqualTo(givenPage);
        Assertions.assertThat(sizeCaptor.getValue()).isEqualTo(givenSize);
        Assertions.assertThat(sortCaptor.getValue()).isEqualTo(givenSort);
    }

    @DisplayName("블로그 조회 성공 결과는 반환되어야합니다.(kakaoClient기준)")
    @Test
    void findBlog_kakaoClient_returnValue() throws KakaoClientException {
        final FindBlogPostRequest givenRequest = FindBlogPostRequest.mapped("givenQuery", null, null, null);
        final SearchBlogMeta givenMeta = new SearchBlogMeta(1, 1, true);
        final SearchBlogDocument givenDocument = new SearchBlogDocument("title", "contents", "url", "blogName", "thumbnail", OffsetDateTime.now());
        final List<SearchBlogDocument> givenDocuments = List.of(givenDocument);
        final SearchBlogResponse givenResponse = new SearchBlogResponse(givenMeta, givenDocuments);
        BDDMockito.given(mockKakaoClient.searchBlog(any(), any(), any(), any()))
                .willReturn(givenResponse);

        final FindBlogPostResponse response = extraBlogPostFindService.findBlog(givenRequest);

        assertThat(response).isNotNull();
        assertThat(response.meta()).isNotNull();
        assertThat(response.meta().totalCount()).isEqualTo(givenResponse.getMeta().getTotalCount());
        assertThat(response.meta().pageableCount()).isEqualTo(givenResponse.getMeta().getPageableCount());
        assertThat(response.meta().isEnd()).isEqualTo(givenResponse.getMeta().isEnd());
        assertThat(response.documents()).isNotNull();
        assertThat(response.documents()).isNotEmpty();
        assertThat(response.documents().get(0).title()).isEqualTo(givenResponse.getDocuments().get(0).getTitle());
        assertThat(response.documents().get(0).contents()).isEqualTo(givenResponse.getDocuments().get(0).getContents());
        assertThat(response.documents().get(0).blogName()).isEqualTo(givenResponse.getDocuments().get(0).getBlogName());
        assertThat(response.documents().get(0).url()).isEqualTo(givenResponse.getDocuments().get(0).getUrl());
        assertThat(response.documents().get(0).thumbnail()).isEqualTo(givenResponse.getDocuments().get(0).getThumbnail());
        assertThat(response.documents().get(0).datetime()).isEqualTo(givenResponse.getDocuments().get(0).getDatetime());
    }
}