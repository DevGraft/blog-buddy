package blogbuddy.kakaosearch;

import lombok.Getter;

@Getter
public class KakaoSearchException extends Exception {
    private final int status;
    private final String errorType;
    private final String message;

    protected KakaoSearchException(final  int status, final String errorType, final String message) {
        super(message);
        this.status = status;
        this.errorType = errorType;
        this.message = message;
    }

    public static KakaoSearchException mapped(final  int httpStatus, final String errorType, final String message) {
        return new KakaoSearchException(httpStatus, errorType, message);
    }
}
