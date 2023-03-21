package blogbuddy.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BlogSearchHistoryByCountDto {
    private final String keyword;
    private final int count;
}
