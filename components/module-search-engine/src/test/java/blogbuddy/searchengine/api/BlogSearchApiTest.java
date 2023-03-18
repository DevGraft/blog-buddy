package blogbuddy.searchengine.api;

import blogbuddy.searchengine.app.BlogSearchService;
import blogbuddy.searchengine.app.SearchPostItem;
import blogbuddy.searchengine.app.SearchPostResponse;
import blogbuddy.searchengine.config.ObjectMapperConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@DisplayName("블로그 검색 API")
class BlogSearchApiTest {
    private MockMvc mockMvc;
    @InjectMocks
    private BlogSearchApi blogSearchApi;
    @Mock
    private BlogSearchService mockBlogSearchService;
    @Captor
    private ArgumentCaptor<String> keywordCaptor;
    private final ObjectMapper objectMapper = new ObjectMapperConfig().objectMapper();
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(blogSearchApi)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @DisplayName("블로그 검색 정상 결과는 status=Ok(200)입니다.")
    @Test
    void blogSearch_returnOkHttpStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/search/blog"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("전달받은 Param을 서비스에 전달합니다.")
    @Test
    void blogSearch_passesParamToService() throws Exception {
        final String givenKeyword = "Kakao Landing";

        mockMvc.perform(MockMvcRequestBuilders.get("/search/blog")
                .param("keyword", givenKeyword));

        Mockito.verify(mockBlogSearchService, Mockito.times(1)).searchPost(keywordCaptor.capture());
        Assertions.assertThat(keywordCaptor.getValue()).isEqualTo(givenKeyword);
    }

    @DisplayName("블로그 검색 결과는 반환됩니다.")
    @Test
    void blogSearch_returnValue() throws Exception {
        final String givenKeyword = "Kakao Landing";
        final SearchPostItem givenItem = new SearchPostItem("kakao-title", "kakao landing content", "url",
                "PCloud", "thumbnail", OffsetDateTime.now());
        final SearchPostResponse givenResponse = new SearchPostResponse(1, 1, true, List.of(givenItem));
        BDDMockito.given(mockBlogSearchService.searchPost(givenKeyword)).willReturn(givenResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/search/blog")
                        .param("keyword", givenKeyword))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalCount", is(givenResponse.totalCount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageableCount", is(givenResponse.pageableCount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isEnd", is(givenResponse.isEnd())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documents", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documents[0].title", is(givenItem.title())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documents[0].contents", is(givenItem.contents())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documents[0].url", is(givenItem.url())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documents[0].blogName", is(givenItem.blogName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documents[0].thumbnail", is(givenItem.thumbnail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documents[0].datetime", is(convertDateToString(givenItem.datetime()))))
        ;
    }

    private String convertDateToString(OffsetDateTime datetime) {
        final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss.SSSZ", Locale.KOREAN);
        return datetime.format(DATE_TIME_FORMATTER);
    }
}