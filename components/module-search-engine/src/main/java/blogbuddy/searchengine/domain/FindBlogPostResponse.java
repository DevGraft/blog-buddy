package blogbuddy.searchengine.domain;


import java.util.List;

public record FindBlogPostResponse(FindBlogPostMeta meta, List<FindBlogPostDocument> documents) {
}
