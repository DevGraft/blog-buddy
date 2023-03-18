package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.BlogPostFindItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SearchPostItem {
    private final String title;
    private final String contents;
    private final String url;
    private final String thumbnail;
    private final LocalDateTime datetime;

    public static SearchPostItem mapped(final BlogPostFindItem item) {
        return builder()
                .title(item.getTitle())
                .contents(item.getContents())
                .url(item.getUrl())
                .thumbnail(item.getThumbnail())
                .datetime(item.getDatetime())
                .build();
    }
}