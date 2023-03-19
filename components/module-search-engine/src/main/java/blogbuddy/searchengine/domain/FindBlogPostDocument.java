package blogbuddy.searchengine.domain;

import blogbuddy.kakaosearch.SearchBlogDocument;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record FindBlogPostDocument(String title, String contents, String url, String blogName, String thumbnail,
                                   OffsetDateTime datetime) {
    public static FindBlogPostDocument mapped(final SearchBlogDocument document) {
        return builder()
                .title(document.getTitle())
                .contents(document.getContents())
                .blogName(document.getBlogName())
                .url(document.getUrl())
                .thumbnail(document.getThumbnail())
                .datetime(document.getDatetime())
                .build();
    }
}
