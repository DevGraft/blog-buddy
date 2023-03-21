package blogbuddy.searchengine.infra;

import blogbuddy.kakaosearch.KakaoClient;
import blogbuddy.kakaosearch.KakaoClientException;
import blogbuddy.kakaosearch.KakaoSearchBlogDocument;
import blogbuddy.kakaosearch.KakaoSearchBlogMeta;
import blogbuddy.kakaosearch.KakaoSearchBlogResponse;
import blogbuddy.naversearch.NaverClient;
import blogbuddy.naversearch.NaverClientException;
import blogbuddy.naversearch.NaverSearchBlogItem;
import blogbuddy.naversearch.NaverSearchBlogResponse;
import blogbuddy.searchengine.domain.FindBlogPostRequest;
import blogbuddy.searchengine.domain.FindBlogPostResponse;
import blogbuddy.support.advice.exception.RequestException;
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

import java.time.LocalDate;
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

        assertThat(queryCaptor.getValue()).isEqualTo(givenQuery);
        assertThat(pageCaptor.getValue()).isEqualTo(givenPage);
        assertThat(sizeCaptor.getValue()).isEqualTo(givenSize);
        assertThat(sortCaptor.getValue()).isEqualTo(givenSort);
    }

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

    @DisplayName("블로그 조회 성공 결과는 반환되어야합니다.(정상 기준/kakao)")
    @Test
    void findBlog_returnValue() throws KakaoClientException {
        final FindBlogPostRequest givenRequest = FindBlogPostRequest.mapped("givenQuery", null, null, null);
        final KakaoSearchBlogMeta givenMeta = new KakaoSearchBlogMeta(1, 1, true);
        final KakaoSearchBlogDocument givenDocument = new KakaoSearchBlogDocument("title", "contents", "url", "blogName", "thumbnail", OffsetDateTime.now());
        final List<KakaoSearchBlogDocument> givenDocuments = List.of(givenDocument);
        final KakaoSearchBlogResponse givenResponse = new KakaoSearchBlogResponse(givenMeta, givenDocuments);
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
        assertThat(response.documents().get(0).datetime()).isEqualTo(givenResponse.getDocuments().get(0).getDatetime().toLocalDateTime());
    }

    @DisplayName("카카오 Api 호출에 문제가 생겼을 경우 Naver Api 호출합니다.")
    @Test
    void findBlog_apiCallError_route_naver() throws KakaoClientException, NaverClientException {
        final String givenQuery = "givenQuery";
        final Integer givenPage = 1;
        final Integer givenSize = 10;
        final String givenSort = "recency";

        final FindBlogPostRequest givenRequest = FindBlogPostRequest.mapped(givenQuery, givenPage, givenSize, givenSort);
        BDDMockito.given(mockKakaoClient.searchBlog(any(), any(), any(), any()))
                .willThrow(KakaoClientException.mapped(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ErrorType", "호출 에러"));
        try {
            extraBlogPostFindService.findBlog(givenRequest);
        } catch (Throwable ignore) {}

        Mockito.verify(mockNaverClient, Mockito.times(1))
                .searchBlog(queryCaptor.capture(), sizeCaptor.capture(), pageCaptor.capture(), sortCaptor.capture());

        assertThat(queryCaptor.getValue()).isEqualTo(givenQuery);
        assertThat(pageCaptor.getValue()).isEqualTo(givenPage);
        assertThat(sizeCaptor.getValue()).isEqualTo(givenSize);
        assertThat(sortCaptor.getValue()).isEqualTo("date");
    }

    @DisplayName("Naver Api 호출 결과를 형식에 맞추어 반환합니다.")
    @Test
    void findBlog_returnNaverApiValue() throws KakaoClientException, NaverClientException {
        final FindBlogPostRequest givenRequest = FindBlogPostRequest.mapped("givenQuery", null, null, null);
        final NaverSearchBlogItem givenItem = new NaverSearchBlogItem("title", "link", "desc", "bloggerName", "bloggerLink", LocalDate.now());
        final NaverSearchBlogResponse givenResponse = new NaverSearchBlogResponse(100, 1, 10, "", List.of(givenItem));
        BDDMockito.given(mockKakaoClient.searchBlog(any(), any(), any(), any()))
                .willThrow(KakaoClientException.mapped(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ErrorType", "호출 에러"));
        BDDMockito.given(mockNaverClient.searchBlog(any(), any(), any(), any()))
                .willReturn(givenResponse);

        final FindBlogPostResponse response = extraBlogPostFindService.findBlog(givenRequest);

        assertThat(response.meta()).isNotNull();
        assertThat(response.meta().totalCount()).isEqualTo(givenResponse.getTotal());
        assertThat(response.meta().pageableCount()).isEqualTo((givenResponse.getTotal() + givenResponse.getDisplay() - 1) / givenResponse.getDisplay());
        assertThat(response.meta().isEnd()).isFalse();
        assertThat(response.documents()).isNotEmpty();
        assertThat(response.documents().get(0).title()).isEqualTo(givenItem.getTitle());
        assertThat(response.documents().get(0).url()).isEqualTo(givenItem.getLink());
        assertThat(response.documents().get(0).blogName()).isEqualTo(givenItem.getBloggerName());
        assertThat(response.documents().get(0).contents()).isEqualTo(givenItem.getDescription());
    }
}