package cn.ibm.com.assignments004.utils.table;

/**
 * Created by zhuhui.bao on 3/8/2018.
 */
public class CellStyle {

    // 列长度
    private int width;

    /**
     *
     * @param width 列宽度
     */
    public CellStyle(int width) {
        this.width = width;
    }

    public CellStyle() {

    }


    public int getWidth() {
        return width;
    }
}
