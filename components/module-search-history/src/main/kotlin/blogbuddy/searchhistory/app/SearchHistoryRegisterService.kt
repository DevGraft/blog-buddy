package blogbuddy.searchhistory.app

import blogbuddy.searchhistory.domain.SearchBlogHistory
import blogbuddy.searchhistory.domain.SearchBlogHistoryRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class SearchHistoryRegisterService(
    private val searchBlogHistoryRepository: SearchBlogHistoryRepository
) {
    @Transactional
    fun register(request:SearchHistoryRegisterDataRequest) {
        val history = SearchBlogHistory(
            keyword = request.keyword,
            searchAt = request.registerDateTime
        )
        searchBlogHistoryRepository.save(history)
    }
}