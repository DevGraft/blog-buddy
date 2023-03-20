package blogbuddy.naversearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SearchBlogItem {
    private String title; // 블로그 포스트 제목
    private String link; // 블로그 포스트 URL
    private String description; // 블로그 내용 요약
    @JsonProperty("bloggername")
    private String bloggerName; // 블로그 이름
    @JsonProperty("bloggerlink")
    private String bloggerLink; // 블로그 주소
    private String postdate; // 포스트 작성 날짜
}
