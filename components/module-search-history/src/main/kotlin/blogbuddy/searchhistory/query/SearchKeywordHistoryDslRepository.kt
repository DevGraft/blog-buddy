package blogbuddy.searchhistory.query

import blogbuddy.searchhistory.domain.QSearchKeywordHistory.searchKeywordHistory
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class SearchKeywordHistoryDslRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun getMostSearchedKeywords(): List<MostSearchedKeywordDetailDto> {
        return queryFactory.select(
            Projections.fields(
                MostSearchedKeywordDetailDto::class.java,
                searchKeywordHistory.keyword,
                searchKeywordHistory.keyword.count().`as`("count")
            )
        )
            .from(searchKeywordHistory)
            .groupBy(searchKeywordHistory.keyword)
            .orderBy(searchKeywordHistory.keyword.count().desc())
            .limit(10)
            .fetch()
    }
}
