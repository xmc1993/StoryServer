package cn.edu.nju.software.exception;

public class CommentException extends RuntimeException {

	private static final long serialVersionUID = 7093579365218870457L;

	public CommentException() {
		super();
	}

	public CommentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CommentException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommentException(String message) {
		super(message);
	}

	public CommentException(Throwable cause) {
		super(cause);
	}

}
