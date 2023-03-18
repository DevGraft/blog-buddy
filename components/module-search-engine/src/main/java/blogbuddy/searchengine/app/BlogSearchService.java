package blogbuddy.searchengine.app;

import blogbuddy.support.advice.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class BlogSearchService {

    public void searchPost(String keyword) {
        // keyword validation
        if (!StringUtils.hasText(keyword)) {
            throw RequestException.of(HttpStatus.BAD_REQUEST, "keyword 입력은 공백일 수 없습니다.");
        }
        // 검색 요청
        // 이벤트 발생
        // 결과 반환
    }
}
