package blogbuddy.kakaosearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class KakaoSearchBlogMeta {
    /**
     * 검색된 총 문서 수
     */
    @JsonProperty("total_count")
    private int totalCount;
    /**
     * total_count 중 노출 가능 문서 수
     */
    @JsonProperty("pageable_count")
    private int pageableCount;
    /**
     * 요청한 페이지가 마지막 페이지 인지 여부 (true=마지막)
     */
    @JsonProperty("is_end")
    private boolean isEnd;
}
