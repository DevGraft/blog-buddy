package blogbuddy.blog.api;

import blogbuddy.blog.app.MostSearchedBlogItem;
import blogbuddy.blog.app.MostSearchedBlogsResponse;
import blogbuddy.blog.app.MostSearchedBlogsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
@DisplayName("블로그 검색 기록 API")
class BlogSearchHistoryApiTest {
    private MockMvc mockMvc;
    @InjectMocks
    private BlogSearchHistoryApi blogSearchHistoryApi;
    @Mock
    private MostSearchedBlogsService mockMostSearchedBlogsService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(blogSearchHistoryApi).build();
    }

    @DisplayName("인기 블로그 목록 검색 정상 결과는 status=Ok(200)입니다.")
    @Test
    void getMostSearchedBlogs_returnOkHttpStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/search/most-searched-blogs"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("서비스 요청 결과를 반환합니다.")
    @Test
    void getMostSearchedBlogs_returnValueByService() throws Exception {
        final String givenKeyword = "kakao landing";
        final int givenCount = 100000;
        final MostSearchedBlogItem givenItem = new MostSearchedBlogItem(givenKeyword, givenCount);
        final MostSearchedBlogsResponse givenResponse = new MostSearchedBlogsResponse(List.of(givenItem));
        BDDMockito.given(mockMostSearchedBlogsService.getMostSearchedBlogs()).willReturn(givenResponse);


        mockMvc.perform(MockMvcRequestBuilders.get("/search/most-searched-blogs"))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.blogs").isArray())
                .andExpect(jsonPath("$.blogs").isNotEmpty())
                .andExpect(jsonPath("$.blogs[0].keyword").value(givenKeyword))
                .andExpect(jsonPath("$.blogs[0].count").value(givenCount))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(mockMostSearchedBlogsService, Mockito.times(1)).getMostSearchedBlogs();
    }
}