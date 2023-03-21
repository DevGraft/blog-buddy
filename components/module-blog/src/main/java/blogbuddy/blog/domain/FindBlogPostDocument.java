package blogbuddy.blog.domain;

import java.time.LocalDateTime;

public record FindBlogPostDocument(String title, String contents, String url, String blogName, String thumbnail,
                                   LocalDateTime datetime) {

}
