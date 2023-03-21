package blogbuddy.blog.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BlogEvents {
    private final ApplicationEventPublisher publisher;

    public <T extends BlogEvent> void raise(T event) {
        publisher.publishEvent(event);
    }
}
