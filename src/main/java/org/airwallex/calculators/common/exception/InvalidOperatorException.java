package org.airwallex.calculators.common.exception;

import org.airwallex.calculators.common.Operator;

public class InvalidOperatorException extends Exception{
    private Operator op;

    public InvalidOperatorException(String message, Operator op){
        super(String.format("invalid operator: %s, %s", op.getName(), message));
        this.op = op;
    }

    public Operator getOp() {
        return op;
    }
}
