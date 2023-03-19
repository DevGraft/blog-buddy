package blogbuddy.searchengine.domain;

import blogbuddy.kakaosearch.SearchBlogMeta;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record FindBlogPostMeta(int totalCount, int pageableCount, boolean isEnd) {
    public static FindBlogPostMeta mapped(final SearchBlogMeta meta) {
        return builder()
                .totalCount(meta.getTotalCount())
                .pageableCount(meta.getPageableCount())
                .isEnd(meta.isEnd())
                .build();
    }
}
