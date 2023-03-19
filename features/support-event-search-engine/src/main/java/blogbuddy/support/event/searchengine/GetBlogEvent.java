package blogbuddy.support.event.searchengine;

import blogbuddy.support.event.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GetBlogEvent implements Event {
    private final String keyword;

    public static GetBlogEvent create(final String keyword) {
        return new GetBlogEvent(keyword);
    }
}
