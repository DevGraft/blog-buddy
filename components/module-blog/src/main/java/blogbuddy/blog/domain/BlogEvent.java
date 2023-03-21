package blogbuddy.blog.domain;

import java.time.LocalDateTime;

public interface BlogEvent {
    LocalDateTime getRegisterDatetime();
}
