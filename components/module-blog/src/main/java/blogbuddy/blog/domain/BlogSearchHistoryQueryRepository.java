package blogbuddy.blog.domain;

import java.util.List;

public interface BlogSearchHistoryQueryRepository {
    public List<BlogSearchHistoryByCountDto> getBlogSearchHistoryByCount(final int limit);
}
