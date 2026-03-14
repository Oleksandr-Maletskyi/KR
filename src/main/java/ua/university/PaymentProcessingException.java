package ua.university;

public class PaymentProcessingException extends Exception {

    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
