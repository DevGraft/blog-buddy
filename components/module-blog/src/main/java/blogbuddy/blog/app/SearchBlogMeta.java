package blogbuddy.blog.app;


import blogbuddy.blog.domain.FindBlogPostMeta;

public record SearchBlogMeta(int totalCount, int pageableCount, boolean isEnd) {
    public static SearchBlogMeta mapped(final FindBlogPostMeta meta) {
        return new SearchBlogMeta(meta.totalCount(), meta.pageableCount(), meta.isEnd());
    }
}
