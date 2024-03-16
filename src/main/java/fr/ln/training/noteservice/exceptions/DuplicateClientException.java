package fr.ln.training.noteservice.exceptions;

public class DuplicateClientException extends Exception {
    public DuplicateClientException(String message) {
        super(message);
    }
}
