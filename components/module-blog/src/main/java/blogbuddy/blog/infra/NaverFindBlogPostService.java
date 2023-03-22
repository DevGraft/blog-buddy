package blogbuddy.blog.infra;

import blogbuddy.blog.domain.FindBlogPostRequest;
import blogbuddy.blog.domain.FindBlogPostResponse;
import blogbuddy.blog.domain.FindBlogPostService;
import blogbuddy.blog.support.advice.exception.RequestException;
import blogbuddy.naversearch.NaverClient;
import blogbuddy.naversearch.NaverClientException;
import blogbuddy.naversearch.NaverSearchBlogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static blogbuddy.blog.domain.ExceptionConstant.INVALID_ARGUMENT;
import static blogbuddy.blog.domain.ExceptionConstant.SEARCH_BLOG_PARAM_PAGE_INVALID_VALUE;
import static blogbuddy.blog.domain.ExceptionConstant.SEARCH_BLOG_PARAM_SIZE_INVALID_VALUE;
import static blogbuddy.blog.domain.ExceptionConstant.SYSTEM_ERROR;

@RequiredArgsConstructor
@Service
class NaverFindBlogPostService implements FindBlogPostService {
    private final NaverClient naverClient;
    @Override
    public FindBlogPostResponse findBlog(FindBlogPostRequest request) {
        try {
            final NaverSearchBlogResponse naverSearchBlogResponse = naverClient.searchBlog(request.getQuery(), request.getSize(), request.getPage(), getNaverSort(request.getSort()));
            return FindBlogPostResponseMapper.mapped(naverSearchBlogResponse);
        } catch (NaverClientException e) {
            throw convertNaverClientException(e);
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
            default -> "";
        };
    }

    private RequestException convertNaverClientException(final NaverClientException e) {
        if (HttpStatus.BAD_REQUEST.value() != e.getStatus()) {
            return RequestException.of(SYSTEM_ERROR.getStatus(), SYSTEM_ERROR.getMessage());
        }
        return switch (e.getErrorCode()) {
            case "SE02" ->
                    RequestException.of(SEARCH_BLOG_PARAM_SIZE_INVALID_VALUE.getStatus(), SEARCH_BLOG_PARAM_SIZE_INVALID_VALUE.getMessage());
            case "SE03" ->
                    RequestException.of(SEARCH_BLOG_PARAM_PAGE_INVALID_VALUE.getStatus(), SEARCH_BLOG_PARAM_PAGE_INVALID_VALUE.getMessage());
            default -> RequestException.of(INVALID_ARGUMENT.getStatus(), INVALID_ARGUMENT.getMessage());
        };
    }
}
