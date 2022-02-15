package me.desenvolve.batataBank.Exceptions;

public class InvalidTransactionException extends RuntimeException {

    public InvalidTransactionException(String message){
        super(message);
    }
}
