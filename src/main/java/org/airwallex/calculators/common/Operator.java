package org.airwallex.calculators.common;

/**
 * 操作函数
 */
public enum Operator {
    ADD("+", 2),
    SUBTRACT("-", 2),
    MULTIPLY("*", 2),
    DIVISION("/", 2),
    SQRT("sqrt", 1),
    DEFAULT("default", 0);


    private String name;
    private int paramsNum;

    Operator(String name, int paramsNum){
        this.name = name;
        this.paramsNum = paramsNum;
    }

    public static boolean contains(String operator){
        for(Operator op: Operator.values()){
            if(op==DEFAULT){
                continue;
            }
            if(op.name.equals(operator)){
                return true;
            }
        }
        return false;
    }

    public static Operator get(String operator){
        if(contains(operator)){
            for(Operator op: Operator.values()){
                if(op.name.equals(operator)){
                    return op;
                }
            }
        }
        return DEFAULT;
    }

    public String getName() {
        return name;
    }

    public int getParamsNum() {
        return paramsNum;
    }
}
