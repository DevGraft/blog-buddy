package blogbuddy.searchengine.app;

import blogbuddy.support.advice.exception.RequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DisplayName("블로그 검색 Service")
class BlogSearchServiceTest {
    @InjectMocks
    private BlogSearchService blogSearchService;

    @DisplayName("요청 값 [keyword]가 비어있을 경우 예외처리가 발생해야합니다.")
    @Test
    void searchPost_keywordValidationCheck() {
        final String givenKeyword = "";

        final RequestException exception = assertThrows(RequestException.class, () -> blogSearchService.searchPost(givenKeyword));

        assertThat(exception).isNotNull();
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getMessage()).isEqualTo("keyword 입력은 공백일 수 없습니다.");
    }
}