package cn.ibm.com.assignments004.utils.table;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuhui.bao on 3/8/2018.
 */
public class Table {

    private Row row;

    // 首行（表头粗虚线）:0,中间行（输入具体内容行）:1,尾行(表尾粗虚线):2,当中细虚线行:3
    private int rowFlag = 1;

    private int rowCount = 0;

    private int columnCount = 0;

    private int rowIndex = 0;

    private List columnWidthList = new ArrayList();

    private StringBuilder tableString = new StringBuilder();

    public Table(int columnCount) {
        this.columnCount = columnCount;
    }

    public StringBuilder getTableString() {

        return tableString;
    }

    public Row createRow() {

        row = createRow(rowIndex, tableString, rowFlag, columnCount);
        if (tableString.length() != 0) {
            tableString.append("\n");
        }

        rowIndex ++;
        rowCount ++;

        return row;
    }

    public Row createBodyRowLine() {
        rowFlag =3;
        row = createRow(rowCount-1, tableString, rowFlag,columnCount);
        if (tableString.length() != 0) {
            tableString.append("\n");
        }

        CellStyle style = new CellStyle();

        for (int i=0; i<columnCount; i++) {
            row.setCellStyle(style);
        }
        return row;
    }

    public Row createHeaderRowLine() {
        rowFlag = 0;

        row = createRow(0, tableString, rowFlag,columnCount);
        if (tableString.length() != 0) {
            tableString.append("\n");
        }

        return row;
    }

    public Row createFooterRowLine() {
        rowFlag = 2;

        row = createRow(rowCount-1, tableString, rowFlag,columnCount);
        if (tableString.length() != 0) {
            tableString.append("\n");
        }

        CellStyle style = new CellStyle();

        for (int i=0; i<columnCount; i++) {
            row.setCellStyle(style);
        }

        tableString.append("\n");

        return row;
    }

    private Row createRow(int rowIndex, StringBuilder rowValue, int rowFlag, int columnCount) {

        row = new Row(rowIndex, rowValue, rowFlag, columnWidthList, rowCount, columnCount,this);

        return row;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setEndoTypeName(String endoTypeName) {
        if (!StringUtils.isEmpty(endoTypeName)) {
            tableString.append(endoTypeName);
            tableString.append("\n");
        }
    }

    public void setSummarize(String summarize) {
        if (!StringUtils.isEmpty(summarize)) {
            tableString.append(summarize);
            tableString.append("\n");
        }
    }

    public void setTableName(String tableName) {
        if (!StringUtils.isEmpty(tableName)) {
            tableString.append(tableName);
            tableString.append("：");
            //tableString.append("\n");
        }
    }
}
