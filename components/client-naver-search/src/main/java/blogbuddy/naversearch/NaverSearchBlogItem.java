package blogbuddy.naversearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 네이버 블로그 검색 API 검색 결과 클래스
 */
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class NaverSearchBlogItem {
    /**
     * 블로그 포스트 제목
     */
    private String title;
    /**
     * 블로그 포스트 URL
     */
    private String link;
    /**
     * 블로그 내용 요약
     */
    private String description;
    /**
     * 블로그 이름
     */
    @JsonProperty("bloggername")
    private String bloggerName;
    /**
     * 블로그 주소
     */
    @JsonProperty("bloggerlink")
    private String bloggerLink;
    /**
     * 포스트 작성 날짜
     */
    private LocalDate postdate;
}
