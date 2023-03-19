package blogbuddy.searchhistory.handler

import blogbuddy.searchhistory.app.SearchHistoryRegisterDataRequest
import blogbuddy.searchhistory.app.SearchHistoryRegisterService
import blogbuddy.support.event.searchengine.GetBlogEvent
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

private fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()

@ExtendWith(MockitoExtension::class)
@DisplayName("블로그 조회 이벤트 핸들러 테스트")
internal class GetBlogEventHandlerTest {
    @InjectMocks
    private lateinit var blogEventHandler: GetBlogEventHandler
    @Mock
    private lateinit var mockSearchHistoryRegisterService: SearchHistoryRegisterService
    @Captor
    private lateinit var dataRequestCaptor: ArgumentCaptor<SearchHistoryRegisterDataRequest>


    @DisplayName("블로그 조회 이벤트 정보를 서비스에게 전달합니다.")
    @Test
    fun handle_passesEventDataToService() {
        val givenEvent = GetBlogEvent.create("keyword", LocalDateTime.now())

        blogEventHandler.handle(givenEvent)

        Mockito.verify(mockSearchHistoryRegisterService, Mockito.times(1)).register(capture(dataRequestCaptor))
        Assertions.assertThat(dataRequestCaptor.value).isNotNull
        Assertions.assertThat(dataRequestCaptor.value.keyword).isEqualTo(givenEvent.keyword)
        Assertions.assertThat(dataRequestCaptor.value.registerDateTime).isEqualTo(givenEvent.registerDatetime)
    }
}