package blogbuddy.searchengine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BlogPostFindItem {
    private final String title;
    private final String contents;
    private final String url;
    private final String thumbnail;
    private final String datetime;
}
