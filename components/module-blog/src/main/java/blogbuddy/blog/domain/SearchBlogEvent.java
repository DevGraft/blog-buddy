package blogbuddy.blog.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SearchBlogEvent implements BlogEvent {
    private final String keyword;
    private final LocalDateTime registerDatetime;

    public static SearchBlogEvent create(final String keyword, final LocalDateTime registerDatetime) {
        return new SearchBlogEvent(keyword, registerDatetime);
    }
}
