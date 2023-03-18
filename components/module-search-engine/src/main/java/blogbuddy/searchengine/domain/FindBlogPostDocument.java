package blogbuddy.searchengine.domain;

import java.time.OffsetDateTime;

public record FindBlogPostDocument(String title, String contents, String url, String blogName, String thumbnail,
                                   OffsetDateTime datetime) {
}
