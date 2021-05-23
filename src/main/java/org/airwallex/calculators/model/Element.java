package org.airwallex.calculators.model;

import java.io.Serializable;

/**
 * 抽象类： rpn 要素
 */
public abstract  class Element implements Serializable {

    private boolean visible;

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public abstract double getValue();

    public abstract String getName();
}
