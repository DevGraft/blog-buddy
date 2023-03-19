package blogbuddy.searchhistory.domain

import lombok.Builder
import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "SearchKeywordHistory", indexes = [Index(name = "idx_keyword", columnList = "keyword")])
@Entity
class SearchKeywordHistory @Builder constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val keyword: String,
    @Column(nullable = false, updatable = false)
    val searchAt: LocalDateTime,
)