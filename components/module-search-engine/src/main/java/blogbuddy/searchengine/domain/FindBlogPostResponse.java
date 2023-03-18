package blogbuddy.searchengine.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record FindBlogPostResponse(FindBlogPostMeta meta, List<FindBlogPostDocument> documents) {
}
