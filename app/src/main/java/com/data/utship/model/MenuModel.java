package com.data.utship.model;

public class MenuModel {
    private  int  indexId;
    private String title;
    private String subTitle;
    private int drawableId;

    public MenuModel(int indexId, String title, String subtitle,int image) {
        this.indexId = indexId;
        this.title = title;
        this.subTitle = subtitle;
        this.drawableId = image;

    }

    public int getIndexId() { return indexId; }

    public void setIndexId(int indexId) { this.indexId = indexId; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }
    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
