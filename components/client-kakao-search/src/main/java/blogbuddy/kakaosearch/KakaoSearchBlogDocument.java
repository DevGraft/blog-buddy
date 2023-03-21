package blogbuddy.kakaosearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class KakaoSearchBlogDocument {
    /**
     * 블로그 글 제목
     */
    private String title;
    /**
     * 블로그 글 요약
     */
    private String contents;
    /**
     * 블로그 글 URL
     */
    private String url;
    /**
     * 블로그 이름
     */
    @JsonProperty("blogname")
    private String blogName;
    /**
     * 대표 미리보기 이미지 URL
     */
    private String thumbnail;
    /**
     * 블로그 글 작성 시간, ISO 8601
     */
    private OffsetDateTime datetime;
}
