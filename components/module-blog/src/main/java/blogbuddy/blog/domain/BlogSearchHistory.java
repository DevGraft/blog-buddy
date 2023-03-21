package blogbuddy.blog.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "blog_search_history")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class BlogSearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String keyword;
    @Column(nullable = false, updatable = false)
    private LocalDateTime searchAt;

    public static BlogSearchHistory create(final String keyword, final LocalDateTime searchAt) {
        return new BlogSearchHistory(null, keyword, searchAt);
    }
}
