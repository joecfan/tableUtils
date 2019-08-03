package cn.ibm.com.assignments004.utils.table;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhuhui.bao on 3/15/2018.
 */
public class ColumnModel {

    private int year = 0;

    private BigDecimal startingSalary= BigDecimal.ZERO;

    private int numberOfIncrements = 0;

    private BigDecimal increment= BigDecimal.ZERO;

    private BigDecimal incrementAmount = BigDecimal.ZERO;

    private int numberOfDeductions  = 0;

    private BigDecimal deduction = BigDecimal.ZERO;

    private BigDecimal deductionAmount = BigDecimal.ZERO;

    private BigDecimal salaryGrowth = BigDecimal.ZERO;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigDecimal getStartingSalary() {
        return startingSalary;
    }

    public void setStartingSalary(BigDecimal startingSalary) {
        this.startingSalary = startingSalary;
    }

    public int getNumberOfIncrements() {
        return numberOfIncrements;
    }

    public void setNumberOfIncrements(int numberOfIncrements) {
        this.numberOfIncrements = numberOfIncrements;
    }

    public BigDecimal getIncrement() {
        return increment;
    }

    public void setIncrement(BigDecimal increment) {
        this.increment = increment;
    }

    public BigDecimal getIncrementAmount() {
        return incrementAmount;
    }

    public void setIncrementAmount(BigDecimal incrementAmount) {
        this.incrementAmount = incrementAmount;
    }

    public int getNumberOfDeductions() {
        return numberOfDeductions;
    }

    public void setNumberOfDeductions(int numberOfDeductions) {
        this.numberOfDeductions = numberOfDeductions;
    }

    public BigDecimal getDeduction() {
        return deduction;
    }

    public void setDeduction(BigDecimal deduction) {
        this.deduction = deduction;
    }

    public BigDecimal getDeductionAmount() {
        return deductionAmount;
    }

    public void setDeductionAmount(BigDecimal deductionAmount) {
        this.deductionAmount = deductionAmount;
    }

    public BigDecimal getSalaryGrowth() {
        return salaryGrowth;
    }

    public void setSalaryGrowth(BigDecimal salaryGrowth) {
        this.salaryGrowth = salaryGrowth;
    }
}
