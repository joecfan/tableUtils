package cn.ibm.com.assignments004.utils;

import org.springframework.util.NumberUtils;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * @author: zhuhui bao
 * @date: 14:50 2019/7/30
 **/
public class NumberUtil {

    public static <T extends Number>T parseNumber(Scanner sc, Class<T> targetClass) {

        T number;
        try {
            number = NumberUtils.parseNumber(sc.nextLine(), targetClass);
        } catch (NumberFormatException e) {
            System.out.println("Number Format Exception, Please input again:");
            return parseNumber(sc, targetClass);

        }
        return number;
    }

    public static BigDecimal getIncrementAmount(BigDecimal startingSalary, int number, BigDecimal increment) {

        BigDecimal newSalary =  startingSalary;
        increment = increment.add(BigDecimal.valueOf(100));

        for (int i = 0; i < number; i ++) {
            newSalary = newSalary.multiply(increment).divide(BigDecimal.valueOf(100));
        }
        return newSalary.subtract(startingSalary).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
