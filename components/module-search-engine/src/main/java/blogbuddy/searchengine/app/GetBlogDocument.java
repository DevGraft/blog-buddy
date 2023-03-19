package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.FindBlogPostDocument;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record GetBlogDocument(String title, String contents, String url,
                              @JsonProperty("blogname") String blogName, String thumbnail,
                              OffsetDateTime datetime) {
    public static GetBlogDocument mapped(final FindBlogPostDocument item) {
        return builder()
                .title(item.title())
                .contents(item.contents())
                .url(item.url())
                .blogName(item.blogName())
                .thumbnail(item.thumbnail())
                .datetime(item.datetime())
                .build();
    }
}
