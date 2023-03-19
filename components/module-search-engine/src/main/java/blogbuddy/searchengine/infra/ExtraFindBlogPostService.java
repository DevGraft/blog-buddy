package blogbuddy.searchengine.infra;

import blogbuddy.kakaosearch.KakaoSearchClient;
import blogbuddy.kakaosearch.KakaoSearchException;
import blogbuddy.kakaosearch.SearchBlogResponse;
import blogbuddy.searchengine.domain.FindBlogPostRequest;
import blogbuddy.searchengine.domain.FindBlogPostResponse;
import blogbuddy.searchengine.domain.FindBlogPostService;
import blogbuddy.support.advice.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExtraFindBlogPostService implements FindBlogPostService {
    private final KakaoSearchClient kakaoSearchClient;
    @Override
    public FindBlogPostResponse findBlog(final FindBlogPostRequest request) {
        // 요청
        try {
            final SearchBlogResponse searchBlogResponse = kakaoSearchClient.searchBlog(request.getQuery(), request.getSort(), request.getPage(), request.getSize());
            return FindBlogPostResponse.mapped(searchBlogResponse);
        } catch (KakaoSearchException e) {
            throw RequestException.of(HttpStatus.valueOf(e.getStatus()), e.getMessage());
        }
        // 결과 매핑 후 반환
        // 예외처리 발생 시 핸들링
    }
}
