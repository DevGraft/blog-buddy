package blogbuddy.searchhistory.app

import java.time.LocalDateTime

data class SearchKeywordHistoryRegisterDataRequest(
    val keyword:String,
    val registerDateTime:LocalDateTime
)