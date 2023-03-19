package blogbuddy.searchhistory.app

import blogbuddy.searchhistory.domain.SearchBlogHistory
import blogbuddy.searchhistory.domain.SearchBlogHistoryRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

private fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()

@ExtendWith(MockitoExtension::class)
@DisplayName("블로그 검색 기록 저장 기능 테스트")
internal class SearchHistoryRegisterServiceTest {
    @InjectMocks
    private lateinit var searchHistoryRegisterService: SearchHistoryRegisterService
    @Mock
    private lateinit var mockSearchBlogHistoryRepository: SearchBlogHistoryRepository
    @Captor
    private lateinit var historyCaptor: ArgumentCaptor<SearchBlogHistory>

    @DisplayName("검색 기록은 repository save()로 저장됩니다.")
    @Test
    internal fun register_historySaveToRepository() {
        val givenRequest = SearchHistoryRegisterDataRequest("keyword", LocalDateTime.now())

        searchHistoryRegisterService.register(givenRequest)

        Mockito.verify(mockSearchBlogHistoryRepository, Mockito.times(1)).save(capture(historyCaptor))
        Assertions.assertThat(historyCaptor.value).isNotNull
        Assertions.assertThat(historyCaptor.value.keyword).isEqualTo(givenRequest.keyword)
        Assertions.assertThat(historyCaptor.value.searchAt).isEqualTo(givenRequest.registerDateTime)
    }
}