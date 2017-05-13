package cn.edu.nju.software.exception;

public class UpdateAppException extends RuntimeException {

	private static final long serialVersionUID = -3870159520680333161L;

	public UpdateAppException() {
		super();
	}

	public UpdateAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UpdateAppException(String message, Throwable cause) {
		super(message, cause);
	}

	public UpdateAppException(String message) {
		super(message);
	}

	public UpdateAppException(Throwable cause) {
		super(cause);
	}

}
