package org.airwallex.calculators.common.exception;

import org.airwallex.calculators.common.Function;

public class InvalidFunctionException extends Exception {
    private Function function;

    public InvalidFunctionException(String message, Function function){
        super(String.format("invalid function: %s, %s.", function.getName(), message));
        this.function = function;
    }

    public Function getFunction() {
        return function;
    }
}
