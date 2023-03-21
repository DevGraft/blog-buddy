package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.ExceptionConstant;
import blogbuddy.support.advice.exception.RequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("블로그 검색 요청 검사기 테스트")
class GetBlogParamValidatorTest {

    private GetBlogParamValidator validator;
    @BeforeEach
    void setUp() {
        validator = new GetBlogParamValidator();
    }

    @DisplayName("keyword 입력은 필수이며 조건이 충족하지 않는다면 예외처리가 발생합니다.")
    @Test
    void validate_keyword_required_exception() {
        final String givenKeyword = "   ";

        final RequestException exception = assertThrows(RequestException.class, () -> validator.validate(givenKeyword, null, null));

        assertThat(exception).isNotNull();
        assertThat(exception.getStatus()).isEqualTo(ExceptionConstant.SEARCH_BLOG_PARAM_KEYWORD_REQUIRED.getStatus());
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstant.SEARCH_BLOG_PARAM_KEYWORD_REQUIRED.getMessage());
    }

    @DisplayName("page 1~50 범위의 값만 입력 가능하며 그 외는 예외처리가 발생합니다.")
    @Test
    void validate_page_invalid_exception() {
        final String givenKeyword = "Kakao Landing PCloud!";
        final Integer givenPage = 1111;

        final RequestException exception = assertThrows(RequestException.class, () -> validator.validate(givenKeyword, givenPage, null));

        assertThat(exception).isNotNull();
        assertThat(exception.getStatus()).isEqualTo(ExceptionConstant.SEARCH_BLOG_PARAM_PAGE_INVALID_VALUE.getStatus());
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstant.SEARCH_BLOG_PARAM_PAGE_INVALID_VALUE.getMessage());
    }

    @DisplayName("size 1~50 범위의 값만 입력 가능하며 그 외는 예외처리가 발생합니다.")
    @Test
    void validate_size_invalid_exception() {
        final String givenKeyword = "Kakao Landing PCloud!";
        final Integer givenPage = 50;
        final Integer givenSize = 111111;

        final RequestException exception = assertThrows(RequestException.class, () -> validator.validate(givenKeyword, givenPage, givenSize));

        assertThat(exception).isNotNull();
        assertThat(exception.getStatus()).isEqualTo(ExceptionConstant.SEARCH_BLOG_PARAM_SIZE_INVALID_VALUE.getStatus());
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstant.SEARCH_BLOG_PARAM_SIZE_INVALID_VALUE.getMessage());
    }
}