package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.FindBlogPostMeta;

public record GetBlogMeta(int totalCount, int pageableCount, boolean isEnd) {

    public static GetBlogMeta mapped(final FindBlogPostMeta meta) {
        return new GetBlogMeta(meta.totalCount(), meta.pageableCount(), meta.isEnd());
    }
}
