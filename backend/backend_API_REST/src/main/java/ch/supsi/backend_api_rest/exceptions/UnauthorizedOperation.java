package ch.supsi.backend_api_rest.exceptions;

public class UnauthorizedOperation extends Exception{
    public UnauthorizedOperation(String message) {
        super(message);
    }

}
