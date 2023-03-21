package blogbuddy.blog.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BlogSearchEvent implements BlogEvent {
    private final String keyword;
    private final LocalDateTime registerDatetime;

    public static BlogSearchEvent create(final String keyword, final LocalDateTime registerDatetime) {
        return new BlogSearchEvent(keyword, registerDatetime);
    }
}
