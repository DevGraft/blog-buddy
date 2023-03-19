package blogbuddy.support.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Events {
    private final ApplicationEventPublisher publisher;

    public <T extends Event> void raise(T event) {
        publisher.publishEvent(event);
    }
}
