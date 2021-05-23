package org.airwallex.calculators.model;

import org.airwallex.calculators.common.Function;
import org.airwallex.calculators.common.exception.InvalidFunctionException;

import java.util.List;

/**
 * 功能函数要素
 */
public class FunctionElement extends Element {
    private Function function;

    public FunctionElement(Function function){
        this.function = function;
        setVisible(true);
    }

    public Function getFunction() {
        return function;
    }

    void eval(List<Element> elements, List<Element> currentElements, int index) throws InvalidFunctionException{
        switch (function){
            case CLEAR:
                doClear(elements);
                break;
            case UNDO:
                doUndo(elements, currentElements, index);
                break;
            default:
                throw new InvalidFunctionException("unknown function", function);
        }
    }

    void doClear(List<Element> elements){
        elements.clear();
    }

    void doUndo(List<Element> elements, List<Element> currentElements, int index){
        if(!undo(currentElements, index)){
            undo(elements, elements.size());
        }
    }

    boolean undo(List<Element> elements, int index){
        int i = index - 1;
        while(i >=0){
            Element element = elements.get(i);
            if(element instanceof ValueElement){
                elements.remove(element);
                return true;
            }else if(element instanceof OperatorElement){
                List<Element> paramsElements = ((OperatorElement) element).getParamsElement();
                paramsElements.forEach(x->x.setVisible(true));
                elements.remove(element);
                return true;
            }
            i--;
        }
        return false;
    }

    public double getValue() {
        return Double.NaN;
    }

    @Override
    public String getName() {
        return getFunction().getName();
    }
}
