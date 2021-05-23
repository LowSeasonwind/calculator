package org.airwallex.calculators.model;

import org.airwallex.calculators.common.StringUtils;

/**
 * 数字要素
 */
public class ValueElement extends Element {
    private double value;

    public ValueElement(double value){
        this.value = value;
        setVisible(true);
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String getName() {
        return StringUtils.formatDouble(value);
    }
}
