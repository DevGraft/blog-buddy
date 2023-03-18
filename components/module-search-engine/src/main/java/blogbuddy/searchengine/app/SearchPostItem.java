package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.BlogPostFindItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record SearchPostItem(String title, String contents, String url,
                             @JsonProperty(namespace = "blogname") String blogName, String thumbnail,
                             LocalDateTime datetime) {
    public static SearchPostItem mapped(final BlogPostFindItem item) {
        return builder()
                .title(item.getTitle())
                .contents(item.getContents())
                .url(item.getUrl())
                .blogName(item.getBlogName())
                .thumbnail(item.getThumbnail())
                .datetime(item.getDatetime())
                .build();
    }
}
