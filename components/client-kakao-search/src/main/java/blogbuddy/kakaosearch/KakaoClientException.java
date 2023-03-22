package blogbuddy.kakaosearch;

import lombok.Getter;

@Getter
public class KakaoClientException extends RuntimeException {
    private final int status;
    private final String errorType;
    private final String message;

    protected KakaoClientException(final  int status, final String errorType, final String message) {
        super(message);
        this.status = status;
        this.errorType = errorType;
        this.message = message;
    }

    public static KakaoClientException mapped(final  int httpStatus, final String errorType, final String message) {
        return new KakaoClientException(httpStatus, errorType, message);
    }
}
