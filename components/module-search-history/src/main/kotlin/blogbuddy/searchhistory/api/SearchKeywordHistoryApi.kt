package blogbuddy.searchhistory.api

import blogbuddy.searchhistory.app.GetMostSearchHistoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("search/keywords")
@RestController
class SearchKeywordHistoryApi(
    private val getMostSearchHistoryService: GetMostSearchHistoryService
) {

// TODO 입력한 키워드가 몇번 입력되었는지 제공

    @GetMapping("most")
    fun getMostSearchedKeywords() {

    }
}