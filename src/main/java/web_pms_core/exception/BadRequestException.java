package web_pms_core.exception;

public class BadRequestException extends CommonException {

    public BadRequestException(String message) {
        super("BAD_REQUEST", message);
    }
}
