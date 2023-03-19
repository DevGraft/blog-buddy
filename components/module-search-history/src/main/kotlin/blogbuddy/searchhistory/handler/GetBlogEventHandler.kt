package blogbuddy.searchhistory.handler

import blogbuddy.searchhistory.app.SearchKeywordHistoryRegisterDataRequest
import blogbuddy.searchhistory.app.SearchKeywordHistoryRegisterService
import blogbuddy.support.event.EventHandler
import blogbuddy.support.event.searchengine.GetBlogEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class GetBlogEventHandler(
    private val searchKeywordHistoryRegisterService: SearchKeywordHistoryRegisterService
) : EventHandler<GetBlogEvent> {

    @EventListener
    override fun handle(event: GetBlogEvent) {
        // 이벤트 호출
        searchKeywordHistoryRegisterService.register(
            SearchKeywordHistoryRegisterDataRequest(
                keyword = event.keyword,
                registerDateTime = event.registerDatetime
            )
        )
    }
}
