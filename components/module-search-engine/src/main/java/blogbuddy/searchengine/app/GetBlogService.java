package blogbuddy.searchengine.app;

import blogbuddy.searchengine.domain.FindBlogPostRequest;
import blogbuddy.searchengine.domain.FindBlogPostResponse;
import blogbuddy.searchengine.domain.FindBlogPostService;
import blogbuddy.support.advice.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class GetBlogService {
    private final FindBlogPostService findBlogPostService;
    public GetBlogResponse getBlog(final String keyword, final Integer page, final Integer size) {
        /* keyword validation */
        if (!StringUtils.hasText(keyword)) {
            throw RequestException.of(HttpStatus.BAD_REQUEST, "keyword 입력은 공백일 수 없습니다.");
        }
        // 검색 요청
        final FindBlogPostResponse response = findBlogPostService.findBlog(FindBlogPostRequest.mapped(keyword, page, size));
        // 이벤트 발생
        // 결과 반환
        return GetBlogResponse.mapped(response);
    }
}
