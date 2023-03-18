package blogbuddy.kakaosearch;

import lombok.Getter;

@Getter
public class KakaoSearchException extends Exception {
    private final String errorType;
    private final String message;

    protected KakaoSearchException(final String errorType, final String message) {
        super(message);
        this.errorType = errorType;
        this.message = message;
    }

    public static KakaoSearchException mapped(final String errorType, final String message) {
        return new KakaoSearchException(message, errorType);
    }
}
