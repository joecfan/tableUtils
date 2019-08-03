package cn.ibm.com.assignments004;

import cn.ibm.com.assignments004.utils.NumberUtil;
import cn.ibm.com.assignments004.utils.table.CellStyle;
import cn.ibm.com.assignments004.utils.table.ColumnModel;
import cn.ibm.com.assignments004.utils.table.Row;
import cn.ibm.com.assignments004.utils.table.Table;
import org.springframework.util.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        ColumnModel model = new ColumnModel();

        Scanner sc = new Scanner(System.in);
        System.out.println("Please input Starting Salary:");
        BigDecimal startingSalary = NumberUtil.parseNumber(sc, BigDecimal.class);
        if (BigDecimal.ONE.compareTo(startingSalary) > 0) {
            System.out.println("Starting Salary should not less than 1, please input again:");
            startingSalary = NumberUtil.parseNumber(sc, BigDecimal.class);
        }
        model.setStartingSalary(startingSalary);

        System.out.println("Please input Years for prediction:");
        Integer years = NumberUtil.parseNumber(sc, Integer.class);
        if (years < 0) {
            System.out.println("Please input positive Years.");
            years = NumberUtils.parseNumber(sc.nextLine(), Integer.class);
        }

        model.setYear(years);

        System.out.println("Please input Number Increments:");
        Integer numberOfIncrements = NumberUtil.parseNumber(sc, Integer.class);
        if (numberOfIncrements < 0) {
            System.out.println("Please input positive Number Increments.");
            numberOfIncrements = NumberUtils.parseNumber(sc.nextLine(), Integer.class);
        }

        model.setNumberOfIncrements(numberOfIncrements);

        System.out.println("Please input Increment %:");
        BigDecimal increment = NumberUtil.parseNumber(sc, BigDecimal.class);
        if (BigDecimal.ZERO.compareTo(increment) > 0) {
            System.out.println("Please input positive Increment.");
            increment = NumberUtil.parseNumber(sc, BigDecimal.class);
        }
        model.setIncrement(increment);

        System.out.println("Please input Number of Deductions:");
        Integer numberOfDeductions = NumberUtil.parseNumber(sc, Integer.class);
        if (numberOfDeductions < 0) {
            System.out.println("Please input positive Number Deductions.");
            numberOfDeductions = NumberUtils.parseNumber(sc.nextLine(), Integer.class);
        }

        model.setNumberOfDeductions(numberOfDeductions);

        System.out.println("Please input deduction amount:");
        BigDecimal deductionAmount = NumberUtil.parseNumber(sc, BigDecimal.class);
        if (BigDecimal.ZERO.compareTo(deductionAmount) > 0) {
            System.out.println("Please input positive deduction amount:.");
        }
        model.setDeductionAmount(deductionAmount);

        List<ColumnModel> predictionModelList = new ArrayList<>();
        // a.Increment Report
        incrementReport(model, predictionModelList);
        // b.Deduction Report
        deductionReport(model, predictionModelList);
        // c.Prediction
        predictionReport(predictionModelList);

    }

    private static void incrementReport(ColumnModel model, List<ColumnModel> predictionModelList) {
        Table table = new Table(5);

        table.setTableName("a.Increment Report");

        Row row;

        CellStyle style;

        row = table.createHeaderRowLine();

        style = new CellStyle(10);
        row.setCellStyle(style);

        style = new CellStyle(25);
        row.setCellStyle(style);

        style = new CellStyle(25);
        row.setCellStyle(style);

        style = new CellStyle(25);
        row.setCellStyle(style);

        style = new CellStyle(20);
        row.setCellStyle(style);

        row = table.createRow();

        row.createCell("Year");
        row.createCell("Starting Salary");
        row.createCell("Number of Increments");
        row.createCell("Increment %");
        row.createCell("Increment Amount");

        table.createBodyRowLine();
        BigDecimal incrementAmount = BigDecimal.ZERO;
        BigDecimal newStartingSalary = model.getStartingSalary();
        ColumnModel predictionModel;

        for (int i=1; i <= model.getYear(); i++) {
            predictionModel = new ColumnModel();

            row = table.createRow();
            row.createCell(String.valueOf(i));
            predictionModel.setYear(i);

            newStartingSalary = incrementAmount.add(newStartingSalary);
            row.createCell(String.valueOf(newStartingSalary));

            predictionModel.setStartingSalary(newStartingSalary);

            row.createCell(String.valueOf(model.getNumberOfIncrements()));
            row.createCell(String.valueOf(model.getIncrement()));
            incrementAmount = NumberUtil.getIncrementAmount(newStartingSalary, model.getNumberOfIncrements(), model.getIncrement());
            row.createCell(String.valueOf(incrementAmount));

            predictionModel.setIncrementAmount(incrementAmount);
            predictionModelList.add(predictionModel);
        }

        table.createFooterRowLine();

        System.out.println(table.getTableString().toString());
    }

    private static void deductionReport(ColumnModel model, List<ColumnModel> predictionModelList) {
        Table table = new Table(5);

        table.setTableName("b. Deduction Report");

        Row row;

        CellStyle style;

        row = table.createHeaderRowLine();

        style = new CellStyle(10);
        row.setCellStyle(style);

        style = new CellStyle(25);
        row.setCellStyle(style);

        style = new CellStyle(25);
        row.setCellStyle(style);

        style = new CellStyle(25);
        row.setCellStyle(style);

        style = new CellStyle(20);
        row.setCellStyle(style);

        row = table.createRow();

        row.createCell("Year");
        row.createCell("Starting Salary");
        row.createCell("Number of Deductions");
        row.createCell("Deduction %");
        row.createCell("Deduction Amount");

        table.createBodyRowLine();

        BigDecimal newStartingSalary = model.getStartingSalary();
        BigDecimal deductionAmount = BigDecimal.ZERO;
        BigDecimal deduction;
        for (int i=1; i <= model.getYear(); i++) {

            row = table.createRow();
            row.createCell(String.valueOf(i));
            newStartingSalary = newStartingSalary.subtract(deductionAmount);
            row.createCell(String.valueOf(newStartingSalary));
            row.createCell(String.valueOf(model.getNumberOfDeductions()));

            deductionAmount = model.getDeductionAmount().multiply(BigDecimal.valueOf(model.getNumberOfDeductions()));
            deduction = deductionAmount.divide(newStartingSalary,2, RoundingMode.HALF_UP).divide(BigDecimal.valueOf(model.getNumberOfDeductions()),2, RoundingMode.HALF_UP);

            row.createCell(String.valueOf(deduction.multiply(BigDecimal.valueOf(100))));

            row.createCell(String.valueOf(deductionAmount));
            for (ColumnModel model1 : predictionModelList) {
                if (i == model1.getYear()) {
                    model1.setDeductionAmount(deductionAmount);
                    break;
                }
            }
        }

        table.createFooterRowLine();

        System.out.println(table.getTableString().toString());
    }

    private static void predictionReport(List<ColumnModel> predictionModelList) {
        Table table = new Table(5);

        table.setTableName("c. Prediction Report");

        Row row;

        CellStyle style;

        row = table.createHeaderRowLine();

        style = new CellStyle(10);
        row.setCellStyle(style);

        style = new CellStyle(25);
        row.setCellStyle(style);

        style = new CellStyle(25);
        row.setCellStyle(style);

        style = new CellStyle(25);
        row.setCellStyle(style);

        style = new CellStyle(20);
        row.setCellStyle(style);

        row = table.createRow();

        row.createCell("Year");
        row.createCell("Starting Salary");
        row.createCell("Increment Amount");
        row.createCell("Deduction Amount");
        row.createCell("Salary Growth");

        table.createBodyRowLine();
        for (ColumnModel model1 : predictionModelList) {
            row = table.createRow();
            row.createCell(String.valueOf(model1.getYear()));
            row.createCell(String.valueOf(model1.getStartingSalary()));
            row.createCell(String.valueOf(model1.getIncrementAmount()));
            row.createCell(String.valueOf(model1.getDeductionAmount()));

            row.createCell(String.valueOf(model1.getIncrementAmount().subtract(model1.getDeductionAmount())));
        }

        table.createFooterRowLine();

        System.out.println(table.getTableString().toString());
    }


}
