package org.airwallex.calculators;

import org.airwallex.calculators.common.StringUtils;
import org.airwallex.calculators.model.Calculator;

import java.util.Scanner;

/**
 * 命令行输入， 按空格分隔
 */
public class Application {
    public static void main(String[] args) {
        System.out.println("请按照空格分隔输入！！！");
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(!StringUtils.isEmpty(line)){
                String output = calculator.eval(line);
                System.out.println(output);
            }
        }
    }
}
