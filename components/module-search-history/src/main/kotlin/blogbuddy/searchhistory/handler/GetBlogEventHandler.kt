package blogbuddy.searchhistory.handler

import blogbuddy.searchhistory.app.SearchHistoryRegisterDataRequest
import blogbuddy.searchhistory.app.SearchHistoryRegisterService
import blogbuddy.support.event.EventHandler
import blogbuddy.support.event.searchengine.GetBlogEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class GetBlogEventHandler(
    private val searchHistoryRegisterService: SearchHistoryRegisterService
) : EventHandler<GetBlogEvent> {

    @EventListener
    override fun handle(event: GetBlogEvent) {
        // 이벤트 호출
        searchHistoryRegisterService.register(
            SearchHistoryRegisterDataRequest(
                keyword = event.keyword,
                registerDateTime = event.registerDatetime
            )
        )
    }
}
