package blogbuddy.searchengine.infra;

import blogbuddy.kakaosearch.KakaoClient;
import blogbuddy.kakaosearch.KakaoClientException;
import blogbuddy.kakaosearch.KakaoSearchBlogResponse;
import blogbuddy.naversearch.NaverClient;
import blogbuddy.naversearch.NaverClientException;
import blogbuddy.naversearch.NaverSearchBlogResponse;
import blogbuddy.searchengine.domain.FindBlogPostRequest;
import blogbuddy.searchengine.domain.FindBlogPostResponse;
import blogbuddy.searchengine.domain.FindBlogPostService;
import blogbuddy.support.advice.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
public class ExtraFindBlogPostService implements FindBlogPostService {
    private final KakaoClient kakaoClient;
    private final NaverClient naverClient;
    @Override
    public FindBlogPostResponse findBlog(final FindBlogPostRequest request) {
        try {
            final KakaoSearchBlogResponse kakaoSearchBlogResponse = kakaoClient.searchBlog(request.getQuery(), request.getSort(), request.getPage(), request.getSize());
            return FindBlogPostResponseProvider.mapped(kakaoSearchBlogResponse);
        } catch (KakaoClientException e) {
            if (HttpStatus.INTERNAL_SERVER_ERROR.value() <= e.getStatus()) {
                try {
                    final NaverSearchBlogResponse naverSearchBlogResponse = naverClient.searchBlog(request.getQuery(), request.getSize(), request.getPage(), getNaverSort(request.getSort()));
                    return FindBlogPostResponseProvider.mapped(naverSearchBlogResponse);
                } catch (NaverClientException ex) {
                    throw RequestException.of(HttpStatus.valueOf(ex.getStatus()), ex.getMessage());
                }

            }
            throw RequestException.of(HttpStatus.valueOf(e.getStatus()), e.getMessage());
        }
    }

    /**
     * NaverClient에 맞는 정렬 키워드로 변경.
     * @param sort 정렬 키워드 (accuracy, recency)
     * @return sort, "sim", "date"
     */
    private String getNaverSort(final String sort) {
        if (!StringUtils.hasText(sort)) return sort;

        return switch (sort) {
            case "accuracy" -> "sim";
            case "recency" -> "date";
            default -> sort;
        };
    }
}
