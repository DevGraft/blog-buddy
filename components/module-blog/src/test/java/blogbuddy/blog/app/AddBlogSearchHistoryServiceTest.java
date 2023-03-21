package blogbuddy.blog.app;

import blogbuddy.blog.domain.BlogSearchHistory;
import blogbuddy.blog.domain.BlogSearchHistoryRepository;
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
@DisplayName("블로그 검색 기록 추가 서비스")
class AddBlogSearchHistoryServiceTest {
    @InjectMocks
    private AddBlogSearchHistoryService addBlogSearchHistoryService;
    @Mock
    private BlogSearchHistoryRepository mockBlogSearchHistoryRepository;
    @Captor
    private ArgumentCaptor<BlogSearchHistory> historyArgumentCaptor;

    @DisplayName("블로그 검색 기록 저장을 리포지토리에 요청합니다.")
    @Test
    void addBlogHistory_saveEntityToRepository() {
        final String givenKeyword = "keyword";
        final LocalDateTime givenSearchAt = LocalDateTime.now();

        addBlogSearchHistoryService.addBlogHistory(givenKeyword, givenSearchAt);

        Mockito.verify(mockBlogSearchHistoryRepository, Mockito.times(1)).save(historyArgumentCaptor.capture());
        Assertions.assertThat(historyArgumentCaptor.getValue()).isNotNull();
        Assertions.assertThat(historyArgumentCaptor.getValue().getKeyword()).isEqualTo(givenKeyword);
        Assertions.assertThat(historyArgumentCaptor.getValue().getSearchAt()).isEqualTo(givenSearchAt);
    }

    @DisplayName("키워드가 비어 있다면 저장하지 않습니다.")
    @Test
    void addBlogHistory_isEmptyAndReturn() {
        final String givenKeyword = "";
        final LocalDateTime givenSearchAt = LocalDateTime.now();

        addBlogSearchHistoryService.addBlogHistory(givenKeyword, givenSearchAt);

        Mockito.verify(mockBlogSearchHistoryRepository, Mockito.times(0)).save(historyArgumentCaptor.capture());
    }
}