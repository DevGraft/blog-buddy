package blogbuddy.blog.domain;


public record FindBlogPostMeta(int totalCount, int pageableCount, boolean isEnd){}