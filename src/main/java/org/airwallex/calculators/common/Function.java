package org.airwallex.calculators.common;

/**
 * 功能函数
 */
public enum Function {

    CLEAR("clear"),
    UNDO("undo"),
    DEFAULT("default");

    private String name;
    Function(String name){
        this.name = name;
    }

    public static boolean contains(String function){
        for(Function func: Function.values()){
            if(func==DEFAULT){
                continue;
            }
            if(func.name.equals(function)){
                return true;
            }
        }
        return false;
    }

    public static Function get(String function){
        if(contains(function)){
            for(Function func: Function.values()){
                if(func.name.equals(function)){
                    return func;
                }
            }
        }
        return DEFAULT;
    }

    public String getName() {
        return name;
    }
}
