package blogbuddy.searchhistory.app

import blogbuddy.searchhistory.domain.SearchKeywordHistory
import blogbuddy.searchhistory.domain.SearchKeywordHistoryRepository
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
internal class SearchKeywordHistoryRegisterServiceTest {
    @InjectMocks
    private lateinit var searchKeywordHistoryRegisterService: SearchKeywordHistoryRegisterService
    @Mock
    private lateinit var mockSearchKeywordHistoryRepository: SearchKeywordHistoryRepository
    @Captor
    private lateinit var historyCaptor: ArgumentCaptor<SearchKeywordHistory>

    @DisplayName("검색 기록은 repository save()로 저장됩니다.")
    @Test
    internal fun register_historySaveToRepository() {
        val givenRequest = SearchKeywordHistoryRegisterDataRequest("keyword", LocalDateTime.now())

        searchKeywordHistoryRegisterService.register(givenRequest)

        Mockito.verify(mockSearchKeywordHistoryRepository, Mockito.times(1)).save(capture(historyCaptor))
        Assertions.assertThat(historyCaptor.value).isNotNull
        Assertions.assertThat(historyCaptor.value.keyword).isEqualTo(givenRequest.keyword)
        Assertions.assertThat(historyCaptor.value.searchAt).isEqualTo(givenRequest.registerDateTime)
    }
}