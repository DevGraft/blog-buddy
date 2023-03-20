package blogbuddy.support.event;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@SpringBootTest(classes = {Events.class, EventsTest.TestEventHandler.class})
@DisplayName("이벤트 테스트")
class EventsTest {
    @Autowired
    private Events events;
    @Autowired
    private TestEventHandler testEventHandler;

    @DisplayName("TestEvent가 발생합니다.")
    @Test
    void raisa_callTest() {
        final TestEvent givenEvent = new TestEvent();

        events.raise(givenEvent);

        Assertions.assertThat(testEventHandler.getPassesEvent()).isEqualTo(givenEvent);
    }

    public static class TestEvent implements Event {
        @Override
        public LocalDateTime getRegisterDatetime() {
            return null;
        }
    }

    @Component
    public static class TestEventHandler implements EventHandler<TestEvent> {
        private TestEvent passesEvent;
        @EventListener
        @Override
        public void handle(final TestEvent event) {
            this.passesEvent = event;
        }

        public TestEvent getPassesEvent() {
            return this.passesEvent;
        }
    }
}
