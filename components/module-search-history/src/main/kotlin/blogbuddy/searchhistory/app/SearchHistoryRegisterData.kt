package blogbuddy.searchhistory.app

import java.time.LocalDateTime

data class SearchHistoryRegisterDataRequest(
    val keyword:String,
    val registerDateTime:LocalDateTime
)