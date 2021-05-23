package org.airwallex.calculators.common.exception;

public class InvalidRPNException extends Exception {
    private String rpnString;

    public InvalidRPNException(String rpnString, String message){
        super(String.format("invalid rpn string: %s, %s.", rpnString, message));
        this.rpnString = rpnString;
    }

    public String getRpnString() {
        return rpnString;
    }
}
