package blogbuddy.searchhistory.domain

import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "SearchKeywordHistory")
@Entity
class SearchKeywordHistory constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val keyword: String,
    @Column(nullable = false, updatable = false)
    val searchAt: LocalDateTime,
)
