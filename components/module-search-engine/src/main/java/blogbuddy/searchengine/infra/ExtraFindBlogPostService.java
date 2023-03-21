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

import static blogbuddy.searchengine.domain.ExceptionConstant.INVALID_ARGUMENT;
import static blogbuddy.searchengine.domain.ExceptionConstant.SEARCH_BLOG_PARAM_KEYWORD_REQUIRED;
import static blogbuddy.searchengine.domain.ExceptionConstant.SEARCH_BLOG_PARAM_PAGE_INVALID_VALUE;
import static blogbuddy.searchengine.domain.ExceptionConstant.SEARCH_BLOG_PARAM_SIZE_INVALID_VALUE;
import static blogbuddy.searchengine.domain.ExceptionConstant.SYSTEM_ERROR;

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
            if (HttpStatus.UNAUTHORIZED.value() == e.getStatus() || HttpStatus.INTERNAL_SERVER_ERROR.value() <= e.getStatus()) {
                try {
                    final NaverSearchBlogResponse naverSearchBlogResponse = naverClient.searchBlog(request.getQuery(), request.getSize(), request.getPage(), getNaverSort(request.getSort()));
                    return FindBlogPostResponseProvider.mapped(naverSearchBlogResponse);
                } catch (NaverClientException ex) {
                    throw convertNaverClientException(ex);
                }

            }
            throw convertKakaoClientException(e);
        }
    }

    /**
     * NaverClient에 맞는 정렬 키워드로 변경.
     *
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

    private RequestException convertKakaoClientException(final KakaoClientException e) {
        if (HttpStatus.BAD_REQUEST.value() != e.getStatus()) {
            return RequestException.of(SYSTEM_ERROR.getStatus(), SYSTEM_ERROR.getMessage());
        }
        switch (e.getErrorType()) {
            case "MissingParameter":
                return RequestException.of(SEARCH_BLOG_PARAM_KEYWORD_REQUIRED.getStatus(), SEARCH_BLOG_PARAM_KEYWORD_REQUIRED.getMessage());
            default:
                return RequestException.of(INVALID_ARGUMENT.getStatus(), INVALID_ARGUMENT.getMessage());
        }
    }

    private RequestException convertNaverClientException(final NaverClientException e) {
        if (HttpStatus.BAD_REQUEST.value() != e.getStatus()) {
            return RequestException.of(SYSTEM_ERROR.getStatus(), SYSTEM_ERROR.getMessage());
        }
        switch (e.getErrorCode()) {
            case "SE02": return RequestException.of(SEARCH_BLOG_PARAM_SIZE_INVALID_VALUE.getStatus(), SEARCH_BLOG_PARAM_SIZE_INVALID_VALUE.getMessage());
            case "SE03": return RequestException.of(SEARCH_BLOG_PARAM_PAGE_INVALID_VALUE.getStatus(), SEARCH_BLOG_PARAM_PAGE_INVALID_VALUE.getMessage());
            default:  return RequestException.of(INVALID_ARGUMENT.getStatus(), INVALID_ARGUMENT.getMessage());
        }
    }
}
