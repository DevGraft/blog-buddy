package blogbuddy.blog.infra;

import blogbuddy.blog.domain.FindBlogPostRequest;
import blogbuddy.blog.support.advice.exception.RequestException;
import blogbuddy.kakaosearch.KakaoClientException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@DisplayName("서킷 브레이커 역할 블로그 포스트 조회 서비스")
class CircuitFindBlogPostServiceTest {
    @InjectMocks
    private CircuitFindBlogPostService circuitFindBlogPostService;
    @Mock
    private CircuitProvider mockCircuitProvider;
    @Mock
    private KakaoFindBlogPostService mockKakaoFindBlogPostService;
    @Mock
    private NaverFindBlogPostService mockNaverFindBlogPostService;
    @Captor
    private ArgumentCaptor<FindBlogPostRequest> requestCaptor;

    @DisplayName("서킷이 닫혀있다면 카카오 Service를 호출합니다.")
    @Test
    void findBlog_circuitCloseCallOnKakao() {
        final FindBlogPostRequest givenRequest = FindBlogPostRequest.mapped("givenQuery", 1, 10, "recency");
        BDDMockito.given(mockCircuitProvider.isCircuitOpen()).willReturn(false);

        circuitFindBlogPostService.findBlog(givenRequest);

        Mockito.verify(mockKakaoFindBlogPostService, Mockito.times(1)).findBlog(requestCaptor.capture());
        Assertions.assertThat(requestCaptor.getValue()).isNotNull();
        Assertions.assertThat(requestCaptor.getValue().getQuery()).isEqualTo(givenRequest.getQuery());
        Assertions.assertThat(requestCaptor.getValue().getPage()).isEqualTo(givenRequest.getPage());
        Assertions.assertThat(requestCaptor.getValue().getSort()).isEqualTo(givenRequest.getSort());
    }

    @DisplayName("카카오 클라이언트 익셉션 400 이내면 예외처리를 발생합니다.")
    @Test
    void findBlog_throwKakaoClientException() {
        final FindBlogPostRequest givenRequest = FindBlogPostRequest.mapped("givenQuery", 1, 10, "recency");
        BDDMockito.given(mockCircuitProvider.isCircuitOpen()).willReturn(false);
        BDDMockito.given(mockKakaoFindBlogPostService.findBlog(any())).willThrow(KakaoClientException.mapped(HttpStatus.BAD_REQUEST.value(), "", ""));

        RequestException exception = assertThrows(RequestException.class, () -> circuitFindBlogPostService.findBlog(givenRequest));

        Assertions.assertThat(exception).isNotNull();
    }

    @DisplayName("카카오 클라이언트 익셉션 401&500이상이면 서킷 프로바이더에 Open상태 전환을 요청합니다.")
    @Test
    void findBlog_throwKakaoClientException2() {
        final FindBlogPostRequest givenRequest = FindBlogPostRequest.mapped("givenQuery", 1, 10, "recency");
        BDDMockito.given(mockCircuitProvider.isCircuitOpen()).willReturn(false);
        BDDMockito.given(mockKakaoFindBlogPostService.findBlog(any())).willThrow(KakaoClientException.mapped(HttpStatus.INTERNAL_SERVER_ERROR.value(), "", ""));

        try {
            circuitFindBlogPostService.findBlog(givenRequest);
        }catch (Throwable ignore) {}

        Mockito.verify(mockCircuitProvider, Mockito.times(1)).circuitOpen();
    }

    @DisplayName("서킷이 열려있다면 네이버 Service를 호출합니다.")
    @Test
    void findBlog_circuitOpenCallOnKakao() {
        final FindBlogPostRequest givenRequest = FindBlogPostRequest.mapped("givenQuery", 1, 10, "recency");
        BDDMockito.given(mockCircuitProvider.isCircuitOpen()).willReturn(true);

        circuitFindBlogPostService.findBlog(givenRequest);

        Mockito.verify(mockNaverFindBlogPostService, Mockito.times(1)).findBlog(requestCaptor.capture());
        Assertions.assertThat(requestCaptor.getValue()).isNotNull();
        Assertions.assertThat(requestCaptor.getValue().getQuery()).isEqualTo(givenRequest.getQuery());
        Assertions.assertThat(requestCaptor.getValue().getPage()).isEqualTo(givenRequest.getPage());
        Assertions.assertThat(requestCaptor.getValue().getSort()).isEqualTo(givenRequest.getSort());
    }
}
