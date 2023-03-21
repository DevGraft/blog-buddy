package blogbuddy.blog.app;

import blogbuddy.blog.domain.BlogEvents;
import blogbuddy.blog.domain.BlogSearchEvent;
import blogbuddy.blog.domain.FindBlogPostRequest;
import blogbuddy.blog.domain.FindBlogPostResponse;
import blogbuddy.blog.domain.FindBlogPostService;
import blogbuddy.blog.domain.LocalDateTimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogSearchService {
    private final BlogSearchParamValidator paramValidator;
    private final FindBlogPostService findBlogPostService;
    private final LocalDateTimeProvider localDateTimeProvider;
    private final BlogEvents blogEvents;

    public BlogSearchResponse searchBlog(final String keyword, final Integer page, final Integer size, final String sort) {
        paramValidator.validate(keyword, page, size);
        // 검색 요청
        final FindBlogPostResponse response = findBlogPostService.findBlog(FindBlogPostRequest.mapped(keyword, page, size, sort));
        // 이벤트 발생
        blogEvents.raise(BlogSearchEvent.create(keyword, localDateTimeProvider.now()));
        // 결과 반환
        return BlogSearchResponse.mapped(response);
    }
}
