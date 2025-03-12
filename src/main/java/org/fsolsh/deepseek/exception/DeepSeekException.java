package org.fsolsh.deepseek.exception;

/**
 * @author fsolsh
 * @date 2025-02-27
 */
public class DeepSeekException extends RuntimeException {
    public DeepSeekException() {
        super();
    }

    public DeepSeekException(String message) {
        super(message);
    }

    public DeepSeekException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeepSeekException(Throwable cause) {
        super(cause);
    }

    protected DeepSeekException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
