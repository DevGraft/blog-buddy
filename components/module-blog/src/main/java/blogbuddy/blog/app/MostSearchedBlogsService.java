package blogbuddy.blog.app;

import blogbuddy.blog.domain.BlogSearchHistoryByCountDto;
import blogbuddy.blog.domain.BlogSearchHistoryQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MostSearchedBlogsService {
    private final BlogSearchHistoryQueryRepository queryRepository;
    public MostSearchedBlogsResponse getMostSearchedBlogs() {
        final List<BlogSearchHistoryByCountDto> dtoList = queryRepository.getBlogSearchHistoryByCount(10);
        return MostSearchedBlogsResponse.mapped(dtoList);
    }
}
