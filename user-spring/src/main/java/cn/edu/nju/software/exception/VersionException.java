package cn.edu.nju.software.exception;

public class VersionException extends RuntimeException {

	private static final long serialVersionUID = -20013062863755581L;

	public VersionException() {
		super();
	}

	public VersionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public VersionException(String message, Throwable cause) {
		super(message, cause);
	}

	public VersionException(String message) {
		super(message);
	}

	public VersionException(Throwable cause) {
		super(cause);
	}
}
