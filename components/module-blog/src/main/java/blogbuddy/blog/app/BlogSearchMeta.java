package blogbuddy.blog.app;


import blogbuddy.blog.domain.FindBlogPostMeta;

public record BlogSearchMeta(int totalCount, int pageableCount, boolean isEnd) {
    public static BlogSearchMeta mapped(final FindBlogPostMeta meta) {
        return new BlogSearchMeta(meta.totalCount(), meta.pageableCount(), meta.isEnd());
    }
}
