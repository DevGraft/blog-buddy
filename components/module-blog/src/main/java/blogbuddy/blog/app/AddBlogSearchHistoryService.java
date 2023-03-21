package blogbuddy.blog.app;

import blogbuddy.blog.domain.BlogSearchHistory;
import blogbuddy.blog.domain.BlogSearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AddBlogSearchHistoryService {
    private final BlogSearchHistoryRepository blogSearchHistoryRepository;
    public void addBlogHistory(final String keyword, final LocalDateTime searchAt) {
        if (!StringUtils.hasText(keyword)) return;
        blogSearchHistoryRepository.save(BlogSearchHistory.create(keyword, searchAt));
    }
}
