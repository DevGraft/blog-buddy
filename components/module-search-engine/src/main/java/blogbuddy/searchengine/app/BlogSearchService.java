package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.BlogPostFindRequest;
import blogbuddy.searchengine.domain.BlogPostFindResponse;
import blogbuddy.searchengine.domain.BlogPostFindService;
import blogbuddy.support.advice.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class BlogSearchService {
    private final BlogPostFindService blogPostFindService;
    public SearchPostResponse searchPost(String keyword) {
        /* keyword validation */
        if (!StringUtils.hasText(keyword)) {
            throw RequestException.of(HttpStatus.BAD_REQUEST, "keyword 입력은 공백일 수 없습니다.");
        }
        // 검색 요청
        final BlogPostFindResponse response = blogPostFindService.findBlog(BlogPostFindRequest.mapped(keyword));
        // 이벤트 발생
        // 결과 반환
        return SearchPostResponse.mapped(response);
    }
}
