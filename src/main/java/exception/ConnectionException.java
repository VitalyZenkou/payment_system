package exception;

public class ConnectionException extends RuntimeException {

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException() {
        super("Can't connect to payment_system base");
    }
}
