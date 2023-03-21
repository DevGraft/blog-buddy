package blogbuddy.naversearch;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 네이버 블로그 검색 API 요청에 대한 응답 정보를 담는 클래스.
 */
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class NaverSearchBlogResponse {
    /**
     * 총 검색 결과 개수
     */
    private Integer total;
    /**
     * 검색 시작 위치
     */
    private Integer start;
    /**
     * 한 번에 표시할 검색 결과 개수
     */
    private Integer display;
    /**
     * 검색 결과를 생성하는 시간
     */
    private String lastBuildDate;
    /**
     * 검색 결과
     */
    private List<NaverSearchBlogItem> items;
}

