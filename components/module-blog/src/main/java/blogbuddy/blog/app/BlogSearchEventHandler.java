package blogbuddy.blog.app;

import blogbuddy.blog.domain.BlogSearchEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class BlogSearchEventHandler {
    private final AddBlogSearchHistoryService addBlogSearchHistoryService;

    @Async
    @EventListener
    public void handle(final BlogSearchEvent event) {
        addBlogSearchHistoryService.addBlogHistory(event.getKeyword(), event.getRegisterDatetime());
    }
}
