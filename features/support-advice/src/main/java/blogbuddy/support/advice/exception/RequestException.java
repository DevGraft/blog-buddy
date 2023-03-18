package blogbuddy.support.advice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 예외처리 클래스
 */
@Getter
public class RequestException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    /**
     * 기본 예외처리 생성자
     */
    protected RequestException() {
        super(HttpStatus.BAD_REQUEST.getReasonPhrase());
        this.status = HttpStatus.BAD_REQUEST;
        this.message = status.getReasonPhrase();
    }

    /**
     * Status-400 기본 예외처리 생성자
     *
     * @param message 예외처리 메세지
     */
    protected RequestException(final String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.message = message;
    }

    /**
     * 예외처리 생성자
     *
     * @param message 예외처리 메세지
     * @param status  예외처리 상태 코드
     */
    protected RequestException(final HttpStatus status, final String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public static RequestException of(final HttpStatus status, final String message) {
        return new RequestException(status, message);
    }
}
