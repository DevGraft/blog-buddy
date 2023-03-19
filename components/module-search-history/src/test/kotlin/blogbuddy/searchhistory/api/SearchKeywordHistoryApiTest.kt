package blogbuddy.searchhistory.api

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

private fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()

@ExtendWith(MockitoExtension::class)
@DisplayName("검색 히스토리 Api")
internal class SearchKeywordHistoryApiTest {
    private lateinit var mockMvc: MockMvc
    @InjectMocks
    private lateinit var searchKeywordHistoryApi: SearchKeywordHistoryApi

    @BeforeEach
    internal fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(searchKeywordHistoryApi).build()
    }

    @DisplayName("블로그 검색 베스트 목록 조회 정상 결과는 status=200입니다.")
    @Test
    internal fun getBlogBestList_returnOkHttpStatus() {
        mockMvc.perform(get("/history/search/blog/best"))
            .andExpect(status().isOk)
    }

    @DisplayName("블로그 검색 베스트 목록 조회 시 서비스에서 정보를 갖고 옵니다.")
    @Test
    internal fun getBlogBestList_callService() {

        mockMvc.perform(get("/history/search/blog/best"))
    }
}