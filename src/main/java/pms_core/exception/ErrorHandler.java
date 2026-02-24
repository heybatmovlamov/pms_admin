package pms_core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.format.DateTimeParseException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "Request body is invalid";

        Throwable rootCause = ex;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }

        if (rootCause instanceof DateTimeParseException dtpe) {
            message = "Incorrect time format: '" + dtpe.getParsedString() +
                    "'. Correct format: yyyy-MM-ddTHH:mm:ss";
        }

        return ResponseEntity
                .status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse("BAD_REQUEST", message));
    }

    @ResponseStatus(OK)
    @ExceptionHandler(CameraNotFoundException.class)
    public ErrorResponse handleCameraNotFoundException(CameraNotFoundException ex) {
        addErrorLog(OK, ex.getErrorCode(), ex.getMessage(), "CameraNotFoundException");
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ResponseStatus(OK)
    @ExceptionHandler(BlockedException.class)
    public ErrorResponse handleBlockedException(BlockedException ex) {
        addErrorLog(OK, ex.getErrorCode(), ex.getMessage(), "BlockedException");
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ResponseStatus(OK)
    @ExceptionHandler(NotPaidException.class)
    public ErrorResponse handleNotPaidException(NotPaidException ex) {
        addErrorLog(OK, ex.getErrorCode(), ex.getMessage(), "NotPaidException");
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ResponseStatus(OK)
    @ExceptionHandler(CameraScanException.class)
    public ErrorResponse handleCameraScanException(CameraScanException ex) {
        addErrorLog(OK, ex.getErrorCode(), ex.getMessage(), "CameraScanException");
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse handleBadRequestException(BadRequestException ex) {
        addErrorLog(BAD_REQUEST, ex.getErrorCode(), ex.getMessage(), "BadRequestException");
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNoResource(NoResourceFoundException ex) {
        addErrorLog(NOT_FOUND, "NOT_FOUND", ex.getMessage(), "NoResourceFoundException");
        return new ErrorResponse("NOT_FOUND", "Endpoint not found");
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public ErrorResponse handleDataNotFoundException(DataNotFoundException ex) {
        addErrorLog(NOT_FOUND, ex.getErrorCode(), ex.getMessage(), "DataNotFoundException");
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        String msg = ex.getMessage();
        addErrorLog(BAD_REQUEST, "ERROR", msg, "RuntimeException");

        if (msg == null || msg.isBlank()) {
            return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse("ERROR", "Bad request"));
        }
        if (msg.contains("not found") || msg.contains("Not found")) {
            return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse("NOT_FOUND", msg));
        }
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse("ERROR", msg));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex) {
        log.error("Unhandled exception", ex);
        addErrorLog(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", ex.getMessage(), ex.getClass().getSimpleName());
        return new ErrorResponse("ERROR", "Internal Server Error");
    }

    protected void addErrorLog(HttpStatus httpStatus, String errorCode, String errorMessage, String exceptionType) {
        log.error("HttpStatus: {} | Code: {} | Type: {} | Message: {}", httpStatus, errorCode, exceptionType, errorMessage);
    }
}
