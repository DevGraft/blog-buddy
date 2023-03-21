package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.ExceptionConstant;
import blogbuddy.support.advice.exception.RequestException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GetBlogParamValidator {
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 50;
    public void validate(final String keyword, final Integer page, final Integer size) {
        if (!StringUtils.hasText(keyword)) {
            throw RequestException.of(ExceptionConstant.SEARCH_BLOG_PARAM_KEYWORD_REQUIRED.getStatus(), ExceptionConstant.SEARCH_BLOG_PARAM_KEYWORD_REQUIRED.getMessage());
        }

        if (null != page && (MIN_VALUE > page || MAX_VALUE < page)) {
            throw RequestException.of(ExceptionConstant.SEARCH_BLOG_PARAM_KEYWORD_REQUIRED.getStatus(), ExceptionConstant.SEARCH_BLOG_PARAM_PAGE_INVALID_VALUE.getMessage());
        }

        if (null != size && (MIN_VALUE > size || MAX_VALUE < size)) {
            throw RequestException.of(ExceptionConstant.SEARCH_BLOG_PARAM_SIZE_INVALID_VALUE.getStatus(), ExceptionConstant.SEARCH_BLOG_PARAM_SIZE_INVALID_VALUE.getMessage());
        }
    }

}
