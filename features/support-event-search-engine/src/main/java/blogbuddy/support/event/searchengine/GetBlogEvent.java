package blogbuddy.support.event.searchengine;

import blogbuddy.support.event.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GetBlogEvent implements Event {
    private final String keyword;
    private final LocalDateTime registerDatetime;
    public static GetBlogEvent create(final String keyword, final LocalDateTime registerDatetime) {
        return new GetBlogEvent(keyword, registerDatetime);
    }
}
