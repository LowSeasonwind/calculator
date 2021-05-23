package org.airwallex.calculators.model;

import org.airwallex.calculators.common.Function;
import org.airwallex.calculators.common.Operator;
import org.airwallex.calculators.common.StringUtils;
import org.airwallex.calculators.common.exception.InvalidRPNException;

import java.util.ArrayList;
import java.util.List;

/**
 * 计算器类
 */
public class Calculator {
    private volatile List<Element> elements;
    private volatile List<Element> currentElements;

    public Calculator(){
        this.elements = new ArrayList<>();
        this.currentElements = new ArrayList<>();
    }

    public synchronized String eval(String input){
        try{
            parse(input);
            return evalExpression();
        }catch (Exception e){
            return e.getMessage();
        }
    }

    /**
     * 解析输入字符串
     */
    void parse(String input) throws InvalidRPNException{
        if(input==null){
            throw new InvalidRPNException("null", "null rpn string");
        }

        String[] strArrays = input.split("\\s");
        if(strArrays.length<=0){
            throw new InvalidRPNException(input, "empty rpn string");
        }
        for(String str: strArrays){
            try{
                double v = Double.parseDouble(str);
                ValueElement element = new ValueElement(v);
                currentElements.add(element);
            }catch (Exception e){
                if(Function.contains(str)){
                    FunctionElement element = new FunctionElement(Function.get(str));
                    currentElements.add(element);
                }else if(Operator.contains(str)){
                    OperatorElement element = new OperatorElement(Operator.get(str));
                    currentElements.add(element);
                }else{
                    throw new InvalidRPNException(input, String.format("unknown element: %s", str));
                }

            }
        }
    }

    /**
     * 执行rpn表达式
     */
    String evalExpression(){
        StringBuilder output = new StringBuilder();
        int index = 0;
        boolean errorFlag = false;
        try{
            while(index<currentElements.size()){
                Element element = currentElements.get(index);
                if(element instanceof  FunctionElement){
                    FunctionElement functionElement = (FunctionElement) element;
                    functionElement.eval(elements, currentElements, index);
                    element.setVisible(false);
                }else if(element instanceof OperatorElement){
                    OperatorElement operatorElement = (OperatorElement) element;
                    errorFlag = operatorElement.eval(elements, currentElements, index, output);
                    if(errorFlag){
                        break;
                    }
                }
                index++;
            }

            for(int i=0; i<index; i++){
                Element element = currentElements.get(i);
                if(element instanceof  ValueElement || element instanceof OperatorElement){
                    elements.add(element);
                }
            }
            buildOutput(elements, null, output);
            if(errorFlag){
                buildErrorOutput(currentElements, index, output);
            }
        }catch (Exception e){
            buildOutput(elements, e, output);
        }
        finally {
            currentElements.clear();
        }
        return output.toString();
    }

    /**
     * 构建输出
     * @param builder
     */
    void buildOutput(List<Element> elements, Exception e, StringBuilder builder){
        builder.append("stack:");
        for(Element element: elements){
            if(element.isVisible()){
                builder.append(String.format("%s ", StringUtils.formatDouble(element.getValue())));
            }
        }
        builder.append("\n");
        if(e!=null){
            builder.append(e.getMessage()).append("\n");
        }
    }

    void buildErrorOutput(List<Element> elements, int index, StringBuilder builder){
        int i = index +1;
        List<String> ops = new ArrayList<>();
        while(i<elements.size()){
            Element element = elements.get(i);
            ops.add(element.getName());
            i++;
        }
        if(!ops.isEmpty()){
            builder.append("(the ");
            for(int j=0;j<ops.size(); j++){
                builder.append(ops.get(j));
                builder.append(" ");
                if(j != ops.size() -1){
                    builder.append("and ");
                }
            }
            builder.append(" were not pushed on to the stack due to the previous error");
        }

    }

}
