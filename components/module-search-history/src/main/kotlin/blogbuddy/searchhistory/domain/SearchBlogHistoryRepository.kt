package blogbuddy.searchhistory.domain

import org.springframework.data.jpa.repository.JpaRepository

interface SearchBlogHistoryRepository:JpaRepository<SearchBlogHistory, Long> {
}