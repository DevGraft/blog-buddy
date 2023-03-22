package blogbuddy.blog.infra;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
class CircuitProvider {
    private static final Duration TIMEOUT = Duration.ofSeconds(60L);
    private boolean circuit;
    private LocalDateTime lastFailureTime = LocalDateTime.now();

    public boolean isCircuitOpen() {
        if (circuit && null != lastFailureTime && 0 < Duration.between(lastFailureTime, LocalDateTime.now()).compareTo(TIMEOUT)) {
            circuit = false;
        }
        return circuit;
    }

    public void circuitOpen() {
        this.circuit = true;
        this.lastFailureTime = LocalDateTime.now();
    }
}
