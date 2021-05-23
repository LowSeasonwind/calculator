package org.airwallex.calculators.common.exception;

import org.airwallex.calculators.common.Operator;

public class MissParamsException extends Exception {
    private Operator op;
    private int paramsNum;

    public MissParamsException(Operator op, int paramsNum){
        super(String.format("operator: %s, need %d params, only find %d.", op.getName(), op.getParamsNum(), paramsNum));
        this.op = op;
        this.paramsNum = paramsNum;
    }

    public Operator getOp() {
        return op;
    }

    public int getParamsNum() {
        return paramsNum;
    }
}
