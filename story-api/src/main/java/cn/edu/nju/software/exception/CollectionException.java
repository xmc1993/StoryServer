package cn.edu.nju.software.exception;

public class CollectionException extends RuntimeException {
	private static final long serialVersionUID = -6049281556976824252L;

	public CollectionException() {
		super();
	}

	public CollectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CollectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public CollectionException(String message) {
		super(message);
	}

	public CollectionException(Throwable cause) {
		super(cause);
	}

}
