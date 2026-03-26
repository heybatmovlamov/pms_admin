package web_pms_core.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private final String errorCode;
    private final String message;

    public CommonException(String errorCode, String message) {
        super(message != null ? message : errorCode);
        this.errorCode = errorCode;
        this.message = message;
    }
}
