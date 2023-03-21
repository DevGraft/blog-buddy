package blogbuddy.blog.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogSearchHistoryRepository extends JpaRepository<BlogSearchHistory, Long> {
}
