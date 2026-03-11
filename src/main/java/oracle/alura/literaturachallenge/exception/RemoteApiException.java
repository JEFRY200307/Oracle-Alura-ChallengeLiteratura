package oracle.alura.literaturachallenge.exception;

public class RemoteApiException extends RuntimeException {

    public RemoteApiException(String message) {
        super(message);
    }

    public RemoteApiException(String message, Throwable cause) {
        super(message, cause);
    }
}

