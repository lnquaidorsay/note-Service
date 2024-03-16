package fr.ln.training.noteservice.exceptions;

public class ClientNotFoundException extends Exception{
    public ClientNotFoundException(String message) {
        super(message);
    }
}
