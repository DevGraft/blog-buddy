package blogbuddy.searchengine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BlogPostFindResponse {
    private final int totalCount;
    private final int pageableCount;
    private final boolean isEnd;
    private final List<BlogPostFindItem> documents;
}
