package blogbuddy.searchhistory.app

import blogbuddy.searchhistory.domain.SearchKeywordHistory
import blogbuddy.searchhistory.domain.SearchKeywordHistoryRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class SearchKeywordHistoryRegisterService(
    private val searchKeywordHistoryRepository: SearchKeywordHistoryRepository
) {
    @Transactional
    fun register(request:SearchKeywordHistoryRegisterDataRequest) {
        val history = SearchKeywordHistory(
            keyword = request.keyword,
            searchAt = request.registerDateTime
        )
        searchKeywordHistoryRepository.save(history)
    }
}