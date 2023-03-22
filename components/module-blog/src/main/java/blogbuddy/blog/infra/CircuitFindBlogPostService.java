package blogbuddy.blog.infra;

import blogbuddy.blog.domain.FindBlogPostRequest;
import blogbuddy.blog.domain.FindBlogPostResponse;
import blogbuddy.blog.domain.FindBlogPostService;
import blogbuddy.blog.support.advice.exception.RequestException;
import blogbuddy.kakaosearch.KakaoClientException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static blogbuddy.blog.domain.ExceptionConstant.INVALID_ARGUMENT;
import static blogbuddy.blog.domain.ExceptionConstant.SEARCH_BLOG_PARAM_KEYWORD_REQUIRED;
import static blogbuddy.blog.domain.ExceptionConstant.SYSTEM_ERROR;

@Primary
@Service
class CircuitFindBlogPostService implements FindBlogPostService {
    private final CircuitProvider circuitProvider;
    private final Map<String, FindBlogPostService> findBlogPostServiceFactory;

    CircuitFindBlogPostService(@Qualifier("kakaoFindBlogPostService") KakaoFindBlogPostService kakaoFindBlogPostService,
                               @Qualifier("naverFindBlogPostService") NaverFindBlogPostService naverFindBlogPostService,
                               CircuitProvider circuitProvider) {
        this.circuitProvider = circuitProvider;
        this.findBlogPostServiceFactory = new HashMap<>();
        this.findBlogPostServiceFactory.put("KAKAO", kakaoFindBlogPostService);
        this.findBlogPostServiceFactory.put("NAVER", naverFindBlogPostService);
    }

    @Override
    public FindBlogPostResponse findBlog(final FindBlogPostRequest request) {
        try {
            return this.findBlogPostServiceFactory.get(updateToCurrentTarget()).findBlog(request);
        } catch (final KakaoClientException kex) {
            if (HttpStatus.UNAUTHORIZED.value() != kex.getStatus() && !HttpStatus.valueOf(kex.getStatus()).is5xxServerError()) throw convertKakaoClientException(kex);
            circuitProvider.circuitOpen();
            return this.findBlogPostServiceFactory.get(updateToCurrentTarget()).findBlog(request);
        }
    }

    private String updateToCurrentTarget() {
        return circuitProvider.isCircuitOpen() ? "NAVER" : "KAKAO";
    }

    private RequestException convertKakaoClientException(final KakaoClientException e) {
        if (HttpStatus.BAD_REQUEST.value() != e.getStatus()) {
            return RequestException.of(SYSTEM_ERROR.getStatus(), SYSTEM_ERROR.getMessage());
        }
        return switch (e.getErrorType()) {
            case "MissingParameter" ->
                    RequestException.of(SEARCH_BLOG_PARAM_KEYWORD_REQUIRED.getStatus(), SEARCH_BLOG_PARAM_KEYWORD_REQUIRED.getMessage());
            default -> RequestException.of(INVALID_ARGUMENT.getStatus(), INVALID_ARGUMENT.getMessage());
        };
    }
}
