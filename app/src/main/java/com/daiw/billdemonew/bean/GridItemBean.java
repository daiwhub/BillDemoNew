package com.daiw.billdemonew.bean;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-07  21:58
 *
 *         ***************************
 */
public class GridItemBean {
    private String text;
    private boolean isSelected;
    private String type;

    public GridItemBean() {
    }

    public GridItemBean(String text, boolean isSelected,String type) {
        this.text = text;
        this.isSelected = isSelected;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
