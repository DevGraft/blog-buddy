package blogbuddy.blog.infra;

import blogbuddy.blog.domain.BlogSearchHistoryByCountDto;
import blogbuddy.blog.domain.BlogSearchHistoryQueryRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static blogbuddy.blog.domain.QBlogSearchHistory.blogSearchHistory;

@RequiredArgsConstructor
@Repository
class BlogSearchHistoryQueryDslRepository implements BlogSearchHistoryQueryRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public List<BlogSearchHistoryByCountDto> getBlogSearchHistoryByCount(final long limit) {
        return queryFactory.select(
                        Projections.fields(BlogSearchHistoryByCountDto.class,
                                blogSearchHistory.keyword,
                                blogSearchHistory.keyword.count().as("count")
                        )
                ).from(blogSearchHistory)
                .groupBy(blogSearchHistory.keyword)
                .orderBy(blogSearchHistory.keyword.count().desc())
                .limit(limit)
                .fetch();
    }
}
