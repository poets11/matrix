package matrix.morpheus;

/**
 * Created by poets11 on 15. 7. 16..
 */
public class AssembleException extends RuntimeException {
    public AssembleException() {
    }

    public AssembleException(String message) {
        super(message);
    }

    public AssembleException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssembleException(Throwable cause) {
        super(cause);
    }

    public AssembleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
