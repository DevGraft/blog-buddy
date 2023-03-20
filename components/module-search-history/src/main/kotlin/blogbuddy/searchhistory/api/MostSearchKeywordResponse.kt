package blogbuddy.searchhistory.api

import blogbuddy.searchhistory.query.MostSearchedKeywordDetailDto

data class MostSearchKeywordResponse(val keywords: List<MostSearchedKeywordDetailDto>)
