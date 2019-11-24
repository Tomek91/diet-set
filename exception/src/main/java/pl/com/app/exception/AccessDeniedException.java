package pl.com.app.exception;

public class AccessDeniedException extends MyException {
    public AccessDeniedException(ExceptionCode exceptionCode, String exceptionMessage) {
        super(exceptionCode, exceptionMessage);
    }
}
