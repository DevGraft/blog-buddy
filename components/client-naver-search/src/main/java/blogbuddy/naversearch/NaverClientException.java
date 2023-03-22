package blogbuddy.naversearch;

import lombok.Getter;

@Getter
public class NaverClientException extends RuntimeException {
    private final int status;
    private final String errorCode; // SE01, SE02, SE03, SE04, SE05, SE99
    private final String errorMessage;

    protected NaverClientException(final  int status, final String errorCode, final String errorMessage) {
        super(errorMessage);
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static NaverClientException mapped(final  int httpStatus, final String errorCode, final String errorMessage) {
        return new NaverClientException(httpStatus, errorCode, errorMessage);
    }
}
