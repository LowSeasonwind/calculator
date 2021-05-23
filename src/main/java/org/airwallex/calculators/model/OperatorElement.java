package org.airwallex.calculators.model;

import org.airwallex.calculators.common.Operator;
import org.airwallex.calculators.common.exception.InvalidOperatorException;
import org.airwallex.calculators.common.exception.MissParamsException;
import java.util.ArrayList;
import java.util.List;

/**
 *   操作符要素
 */
public class OperatorElement extends Element{
    private Operator op;
    private double value;
    private boolean done;

    private List<Element> paramsElement;

    public OperatorElement(Operator op){
        this.op = op;
        this.value = Double.NaN;
        setVisible(true);
        this.paramsElement = new ArrayList<>();
        this.done = false;
    }

    public Operator getOp() {
        return op;
    }

    @Override
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public void setParamsElement(List<Element> paramsElement) {
        this.paramsElement = paramsElement;
    }

    public List<Element> getParamsElement() {
        return paramsElement;
    }

    double eval(double... params) throws MissParamsException, InvalidOperatorException{
        if(params.length<op.getParamsNum()){
            throw new MissParamsException(op, params.length);
        }
        switch (op){
            case ADD:
                return params[0] + params[1];
            case SUBTRACT:
                return params[1] - params[0];
            case MULTIPLY:
                return params[0] * params[1];
            case DIVISION:
                return params[1] / params[0];
            case SQRT:
                return Math.sqrt(params[0]);
            default:
                throw new InvalidOperatorException("unknown operator", op);
        }
    }

    boolean eval(List<Element> elements, List<Element> currentElements, int index, StringBuilder output) throws Exception{
        int paramsNum = op.getParamsNum();
        int i=0;
        boolean errorFlag = false;
        List<Element> params = new ArrayList<>();

        int tmpIndex = index - 1;
        while(tmpIndex>=0 && i<paramsNum){
            Element tmpElement = currentElements.get(tmpIndex);
            if(tmpElement.isVisible()){
                if(tmpElement instanceof ValueElement){
                    tmpElement.setVisible(false);
                    params.add(tmpElement);
                    i++;
                }else if(tmpElement instanceof OperatorElement && ((OperatorElement) tmpElement).isDone()){
                    tmpElement.setVisible(false);
                    params.add(tmpElement);
                    i++;
                }
            }
            tmpIndex--;
        }

        int j = elements.size() - 1;
        while(j>=0 && i<paramsNum){
            Element tmpElement = elements.get(j);
            if(tmpElement.isVisible() && (tmpElement instanceof  OperatorElement || tmpElement instanceof  ValueElement)){
                tmpElement.setVisible(false);
                params.add(tmpElement);
                i++;
            }
            j--;
        }
        if(params.size()<paramsNum){
            output.append(String.format("operator %s (position: %d): insucient parameters\n", getOp().getName(), 2*index+1));
            errorFlag = true;
        }
        double[] values = new double[paramsNum];
        for(int z=0;z<params.size(); z++){
            values[z] =params.get(z).getValue();
        }
        double result = eval(values);
        setValue(result);
        setDone(true);
        setParamsElement(params);
        return errorFlag;
    }

    @Override
    public String getName() {
        return getOp().getName();
    }
}
