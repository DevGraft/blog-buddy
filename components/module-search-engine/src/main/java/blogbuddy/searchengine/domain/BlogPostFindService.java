package blogbuddy.searchengine.domain;

public interface BlogPostFindService {
    BlogPostFindResponse findBlog(final BlogPostFindRequest request);
}
