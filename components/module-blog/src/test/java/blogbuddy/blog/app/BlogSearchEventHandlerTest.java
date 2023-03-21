package blogbuddy.blog.app;

import blogbuddy.blog.domain.BlogSearchEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
@DisplayName("블로그 검색 이벤트 핸들러")
class BlogSearchEventHandlerTest {
    @InjectMocks
    private BlogSearchEventHandler blogSearchEventHandler;
    @Mock
    private AddBlogSearchHistoryService mockAddBlogSearchHistoryService;
    @Captor
    private ArgumentCaptor<String> keywordCaptor;
    @Captor
    private ArgumentCaptor<LocalDateTime> searchAtCaptor;


    @DisplayName("블로그 검색 이벤트의 값을 서비스에 전달")
    @Test
    void handle_passesArgumentToService() {
        final String givenKeyword = "Keyword";
        final LocalDateTime givenSearchAt = LocalDateTime.now();
        final BlogSearchEvent givenEvent = BlogSearchEvent.create(givenKeyword, givenSearchAt);

        blogSearchEventHandler.handle(givenEvent);

        Mockito.verify(mockAddBlogSearchHistoryService, Mockito.times(1)).addBlogHistory(keywordCaptor.capture(), searchAtCaptor.capture());
        Assertions.assertThat(keywordCaptor.getValue()).isNotNull();
        Assertions.assertThat(keywordCaptor.getValue()).isEqualTo(givenKeyword);
        Assertions.assertThat(searchAtCaptor.getValue()).isNotNull();
        Assertions.assertThat(searchAtCaptor.getValue()).isEqualTo(givenSearchAt);
    }
}