package blogbuddy.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BlogSearchHistoryByCountDto {
    private String keyword;
    private Long count;
}
