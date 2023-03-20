package blogbuddy.searchhistory.domain

import org.springframework.data.jpa.repository.JpaRepository

interface SearchKeywordHistoryRepository:JpaRepository<SearchKeywordHistory, Long> {
}