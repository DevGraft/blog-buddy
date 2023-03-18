package blogbuddy.searchengine.domain;

public interface FindBlogPostService {
    FindBlogPostResponse findBlog(final FindBlogPostRequest request);
}
