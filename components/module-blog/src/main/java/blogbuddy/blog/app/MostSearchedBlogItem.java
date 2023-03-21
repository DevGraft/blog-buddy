package blogbuddy.blog.app;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MostSearchedBlogItem {
    private final String keyword;
    private final int count;
}
