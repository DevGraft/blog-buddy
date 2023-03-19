package blogbuddy.searchhistory.domain

import lombok.Builder
import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "SearchBlogHistory", indexes = [Index(name = "idx_keyword", columnList = "keyword")])
@Entity
class SearchBlogHistory @Builder private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val keyword: String,
    @Column(nullable = false, updatable = false)
    val searchAt: LocalDateTime,
)