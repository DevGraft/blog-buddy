package blogbuddy.searchhistory.api

import blogbuddy.searchhistory.query.SearchKeywordHistoryDslRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("search/keywords")
@RestController
class SearchKeywordHistoryApi(
    val searchKeywordQuery:SearchKeywordHistoryDslRepository
) {

// TODO 입력한 키워드가 몇번 입력되었는지 제공

    @GetMapping("most")
    fun getMostSearchedKeywords() : MostSearchKeywordResponse {
        val keywords = searchKeywordQuery.getMostSearchedKeywords()
        return MostSearchKeywordResponse(keywords)
    }
}
