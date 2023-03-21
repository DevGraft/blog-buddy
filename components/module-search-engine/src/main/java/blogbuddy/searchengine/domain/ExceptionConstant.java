package blogbuddy.searchengine.domain;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionConstant {
    SEARCH_BLOG_PARAM_KEYWORD_REQUIRED("keyword parameter required (질의어는 필수입니다.)", HttpStatus.BAD_REQUEST),
    SEARCH_BLOG_PARAM_PAGE_INVALID_VALUE("page can only 1 to 50 (페이지는 1~50만 입력 가능합니다.)", HttpStatus.BAD_REQUEST),
    SEARCH_BLOG_PARAM_SIZE_INVALID_VALUE("size can only 1 to 50 (크기는 1~50만 입력 가능합니다.)", HttpStatus.BAD_REQUEST),

    SYSTEM_ERROR("요청을 정상적으로 처리하기 어려운 상황입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String message;
    private final HttpStatus status;

    private ExceptionConstant(final String message, final HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
