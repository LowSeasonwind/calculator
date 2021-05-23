package org.airwallex.calculators.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StringUtils {
    /**
     *
     * 判断空字符串
     */
    public static boolean isEmpty(String str){
        if(str==null || str.length()==0){
            return true;
        }
        return str.trim().length()<=0;
    }

    /**
     * 格式化数字
     */
    public static String formatDouble(double v){
        BigDecimal bg = new BigDecimal(v).setScale(10, RoundingMode.HALF_UP);
        double num = bg.doubleValue();
        if(Math.round(num)-num==0){
            return String.valueOf((long)num);
        }else{
            return bg.stripTrailingZeros().toPlainString();
        }
    }
}
