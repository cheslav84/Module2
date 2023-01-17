package com.example.finalemodule2.exception;

public class BrokenDeviceException extends RuntimeException {
    public BrokenDeviceException() {
        super();
    }

    public BrokenDeviceException(String message) {
        super(message);
    }

    public BrokenDeviceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BrokenDeviceException(Throwable cause) {
        super(cause);
    }

}
