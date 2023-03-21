package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.FindBlogPostRequest;
import blogbuddy.searchengine.domain.FindBlogPostResponse;
import blogbuddy.searchengine.domain.FindBlogPostService;
import blogbuddy.searchengine.domain.LocalDateTimeProvider;
import blogbuddy.support.event.Events;
import blogbuddy.support.event.searchengine.GetBlogEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetBlogService {
    private final GetBlogParamValidator paramValidator;
    private final FindBlogPostService findBlogPostService;
    private final LocalDateTimeProvider localDateTimeProvider;
    private final Events events;
    public GetBlogResponse getBlog(final String keyword, final Integer page, final Integer size, final String sort) {
        paramValidator.validate(keyword, page, size);
        // 검색 요청
        final FindBlogPostResponse response = findBlogPostService.findBlog(FindBlogPostRequest.mapped(keyword, page, size, sort));
        // 이벤트 발생
        events.raise(GetBlogEvent.create(keyword, localDateTimeProvider.now()));
        // 결과 반환
        return GetBlogResponse.mapped(response);
    }
}
