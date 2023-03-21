package blogbuddy.blog.app;

import blogbuddy.blog.domain.BlogSearchHistoryByCountDto;
import blogbuddy.blog.domain.BlogSearchHistoryQueryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
@DisplayName("인기 블로그 검색 Service")
class MostSearchedBlogsServiceTest {
    @InjectMocks
    private MostSearchedBlogsService mostSearchedBlogsService;
    @Mock
    private BlogSearchHistoryQueryRepository mockQueryRepository;
    @Captor
    private ArgumentCaptor<Integer> limitCaptor;

    @DisplayName("검색 기록 중 가장 많은 건을 찾도록 queryRepository에게 요청합니다.")
    @Test
    void getMostSearchBlogs_passesLimitToQueryRepository() {

        mostSearchedBlogsService.getMostSearchedBlogs();

        Mockito.verify(mockQueryRepository, Mockito.times(1)).getBlogSearchHistoryByCount(limitCaptor.capture());
        Assertions.assertThat(limitCaptor.getValue()).isNotNull();
        Assertions.assertThat(limitCaptor.getValue()).isEqualTo(10);
    }

    @DisplayName("QueryRepository에게 전달받은 값을 형식에 맞게 반환합니다.")
    @Test
    void getMostSearchBlogs_returnValue() {
        final BlogSearchHistoryByCountDto givenDto = new BlogSearchHistoryByCountDto("PCloud의 블로그", 1000);
        BDDMockito.given(mockQueryRepository.getBlogSearchHistoryByCount(anyInt())).willReturn(List.of(givenDto));

        final MostSearchedBlogsResponse response = mostSearchedBlogsService.getMostSearchedBlogs();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBlogs()).isNotEmpty();
        Assertions.assertThat(response.getBlogs().get(0).getKeyword()).isEqualTo(givenDto.getKeyword());
        Assertions.assertThat(response.getBlogs().get(0).getCount()).isEqualTo(givenDto.getCount());
    }
}