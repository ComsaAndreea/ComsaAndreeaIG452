package utils;

public class CustomIllegalArgument extends Exception {
    public CustomIllegalArgument(String errorMessage) {
        super(errorMessage);
    }
}
