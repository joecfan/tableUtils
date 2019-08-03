package cn.ibm.com.assignments004.utils.table;

/**
 * Created by zhuhui.bao on 3/8/2018.
 */
public class Cell {

    private CellStyle style;
    private String value;

    public CellStyle getStyle() {
        return style;
    }

    public void setStyle(CellStyle style) {
        this.style = style;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
