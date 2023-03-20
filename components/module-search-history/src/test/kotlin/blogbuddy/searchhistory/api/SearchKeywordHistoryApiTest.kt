package blogbuddy.searchhistory.api

import blogbuddy.searchhistory.query.MostSearchedKeywordDetailDto
import blogbuddy.searchhistory.query.SearchKeywordHistoryDslRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockitoExtension::class)
@DisplayName("검색 히스토리 Api")
internal class SearchKeywordHistoryApiTest {
    private lateinit var mockMvc: MockMvc
    @InjectMocks
    private lateinit var searchKeywordHistoryApi: SearchKeywordHistoryApi
    @Mock
    private lateinit var mockSearchKeywordQuery: SearchKeywordHistoryDslRepository

    @BeforeEach
    internal fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(searchKeywordHistoryApi).build()
    }

    @DisplayName("블로그 검색 베스트 목록 조회 정상 결과는 status=200입니다.")
    @Test
    internal fun getBlogBestList_returnOkHttpStatus() {
        mockMvc.perform(get("/search/keywords/most"))
            .andExpect(status().isOk)
    }

    @DisplayName("블로그 검색 베스트 목록 조회 시 서비스에서 정보를 갖고 옵니다.")
    @Test
    internal fun getBlogBestList_callService() {
        val givenKeyword = "Kakao Landing"
        val givenCount = 3000L
        val givenDto = MostSearchedKeywordDetailDto(keyword = givenKeyword, givenCount)
        BDDMockito.given(mockSearchKeywordQuery.getMostSearchedKeywords()).willReturn(listOf(givenDto))

        mockMvc.perform(get("/search/keywords/most"))
            .andExpect(jsonPath("$.keywords").isArray)
            .andExpect(jsonPath("$.keywords[0].keyword").value(givenKeyword))
            .andExpect(jsonPath("$.keywords[0].count").value(givenCount))
    }
}
