package com.bigbambu.geodespertador;

/**
 * Created by Sebas on 09/01/2016.
 */
public class ExisteAlarmaException extends Exception {

    public ExisteAlarmaException() { super(); }
    public ExisteAlarmaException(String message) { super(message); }
    public ExisteAlarmaException(String message, Throwable cause) { super(message, cause); }
    public ExisteAlarmaException(Throwable cause) { super(cause); }
}
