package blogbuddy.blog.support.advice;


import blogbuddy.blog.support.advice.result.CommonResult;
import blogbuddy.blog.support.advice.result.SearchResult;
import blogbuddy.blog.support.advice.result.SingleResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.List;

@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    private final List<PathPattern> ignores;

    public ResponseAdvice(@Value("${spring.advice.ignore-url-pattern-list:/v*/api-docs,/docs/**,/swagger-resources/**,/swagger-ui.html,/webjars/**,/swagger/**}")
                          List<String> ignoreUrlPatternList) {
        ignores = ignoreUrlPatternList.stream()
                .map(pattern -> new PathPatternParser().parse(pattern)).toList();
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (ignores.stream().anyMatch(pathPattern -> pathPattern.matches(PathContainer.parsePath(request.getURI().getPath()))))
            return body;
        if (body instanceof CommonResult) {
            final CommonResult commonResult = (CommonResult) body;
            if (!commonResult.isSuccess()) {
                response.setStatusCode(commonResult.getStatus());
            }
            return body;
        } else if (PageImpl.class.isInstance(body)) {
            final PageImpl<Object> pageObject = (PageImpl<Object>) body;
            return SingleResult.success(SearchResult.from(pageObject));
        }

        return null != body ? SingleResult.success(body) : CommonResult.success();
    }
}
