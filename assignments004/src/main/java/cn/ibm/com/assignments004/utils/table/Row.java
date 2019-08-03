package cn.ibm.com.assignments004.utils.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuhui.bao on 3/8/2018.
 */
public class Row {
    // 行索引，第一行:0, 中间行:1,最后行：2
    private int rowIndex;

    // 列索引
    private int columIndex = 0;

    // 首行:0,中间行:1,尾行:2
    private int rowFlag = 1;

    private StringBuilder sbRow;

    private List columnWidthList;

    private int rowCount;
    private int columnCount;

    // 存在换行的情况，记录需要换多少行
    private int changeLinesCount;

    private Map<Integer, String[]> changeLinesMap = new HashMap();

    private Table ahTable;

    public StringBuilder getRowValue() {
        return sbRow;
    }

    public Row(int rowIndex, StringBuilder rowValue, int rowFlag, List columnWidthList, int rowCount, int columnCount, Table ahTable) {
        this.rowIndex = rowIndex;
        this.sbRow = rowValue;
        this.rowFlag = rowFlag;
        this.columnWidthList = columnWidthList;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.ahTable = ahTable;
    }

    /**
     * 没有给cell传值表示创建表头和表尾
     * @param style
     * @return
     */
    public StringBuilder setCellStyle(CellStyle style) {

        // 首行的情况，保存各列宽度
        if (rowFlag == 0) {
            columnWidthList.add(style.getWidth());
        }

        buildTableBorder(style, sbRow);

        columIndex ++;

        return sbRow;
    }

    public StringBuilder createCell(String value) {

        int columnWidth = (int)columnWidthList.get(columIndex);

        value = this.buildValue(value, columnWidth);

        buildCellWithValue(value, sbRow);

        columIndex ++;

        if (columIndex == columnCount) {
            // 存在换行的情况
            if (changeLinesCount > 0) {
                for (int i=1; i<changeLinesCount; i++) {
                    //ahTable.createBodyRowLine();
                    Row row = ahTable.createRow();
                    for (int j=0; j<columnCount; j++) {
                        String[] splits = changeLinesMap.get(j);
                        // 多列表格，如果一列出现换行，其它列没有，那么其它列补空格
                        if (splits == null) {
                            row.createCell("");
                        }
                        // 换行的情况
                        else {
                            // 比如两列出现换行，如果第一列2行，另二列3行的情况，画到第三行的时候，第二列就需要补空格了。
                            if (i > splits.length-1) {
                                row.createCell("");
                            } else {
                                row.createCell(splits[i]);
                            }
                        }

                    }
                }
            }

        }

        return sbRow;
    }

    private String buildValue(String value, int columnWidth) {
        StringBuilder sb = new StringBuilder();

        // 半角空格
        String dbcSpace = " ";

        int sbcCharCount=0;
        int dbcCharCount=0;

        if (value == null) {
            value = "";
        }

        char[] chars = value.toCharArray();

        // 指定的列宽度是全角字符的宽度，算成半角的宽度。
        // 因为UTF-8全角字符长度是3，但是显示效果，两个半角显示的长度和一个全角长度是一样的。
        //int newLength = columnWidth * 2;
        int newLength = columnWidth;

        for (char c : chars) {
            if (String.valueOf(c).getBytes().length == 1) {
                dbcCharCount ++;
            } else if (String.valueOf(c).getBytes().length == 3) {
                sbcCharCount ++;
            }
        }

        int valueLen = dbcCharCount + sbcCharCount * 2;

        if (valueLen < newLength) {
            for (int i=0; i<newLength - valueLen; i++) {
                sb.append(dbcSpace);
            }
        } else if (valueLen > newLength) {
            int tmpChangeLinesCount = valueLen / newLength;
            if (valueLen % newLength > 0) {
                tmpChangeLinesCount = tmpChangeLinesCount + 1;
            }

            String[] splits = new String[tmpChangeLinesCount];
            int endIndex = 0;
            for (int i=0; i<tmpChangeLinesCount; i++) {

                if ((i*columnWidth+columnWidth) > value.length()) {
                    endIndex = value.length();
                } else {
                    endIndex = i*columnWidth+columnWidth;
                }
                //endIndex = (i*columnWidth+columnWidth) > value.length()?value.length():i*columnWidth+columnWidth;

                splits[i] = value.substring(i*columnWidth, endIndex);

                if (i == tmpChangeLinesCount-1) {
                    StringBuilder tmpSb = new StringBuilder();

                    for (int j = 0; j < (newLength-splits[i].length()*2); j++) {
                        tmpSb.append(dbcSpace);
                    }

                    splits[i] = splits[i] + tmpSb.toString();
                }
            }

            changeLinesMap.put(columIndex, splits);

            if (tmpChangeLinesCount > changeLinesCount) {
                changeLinesCount = tmpChangeLinesCount;
            }
            value =  value.substring(0, columnWidth);
        }

        return value + sb.toString();

    }

    private void buildCellWithValue(String value,StringBuilder sb) {

        // 第一列
        if (columIndex == 0) {
            sb.append("┇");

            sb.append(value);

            sb.append("┆");
        }
        // 最后列
        else if (columIndex == columnCount-1) {
            sb.append(value);
            sb.append("┇");
        }
        // 其它列
        else {

            sb.append(value);

            sb.append("┆");

        }
    }

    private void buildTableBorder(CellStyle style, StringBuilder sb) {

        int length;

        if (style.getWidth() != 0) {
            length = style.getWidth();
        } else {
            length = (int)columnWidthList.get(columIndex);
        }

        // 第一行
        if (rowFlag == 0) {
            // 第一列
            if (columIndex == 0) {
                // 第一列包含前后两个制表符
                //sb.append("┏");
                sb.append("┏");

                for (int i=0; i<length; i++ ) {
                        sb.append("┅");
                }
                sb.append("┯");

            } // 最后列
            else if (columIndex == columnCount-1) {
                for (int i=0; i<length; i++ ) {
                    sb.append("┅");
                }
                sb.append("┓");
            }
            // 中间列
            else {
                for (int i=0; i<length; i++ ) {
                        sb.append("┅");
                }
                sb.append("┯");
            }

        }
        // 最后行
        else if (rowFlag == 2) {
            if (columIndex == 0) {
                sb.append("┗");
                for (int i=0; i<length; i++ ) {
                    sb.append("┅");
                }
                sb.append("┻");

            } else if (columIndex == columnCount-1) {
                for (int i=0; i<length; i++ ) {
                    sb.append("┅");
                }
                sb.append("┛");

            } else {
                for (int i=0; i<length; i++ ) {
                    sb.append("┅");
                }
                sb.append("┻");
            }
        } // 中间行
        else if (rowFlag == 3) {
            if (columIndex == 0) {
                sb.append("┇");
                for (int i=0; i<length; i++ ) {
                    sb.append("┄");
                }
                sb.append("┼");

            } else if (columIndex == columnCount-1) {
                for (int i=0; i<length; i++ ) {
                    sb.append("┄");
                }
                sb.append("┇");

            } else {
                for (int i=0; i<length; i++ ) {
                    sb.append("┄");
                }
                sb.append("┼");
            }
        }
    }
}
