package com.yashdalfthegray.songaday.Models;

/**
 * Created by Yash Kulshrestha on 8/10/2015.
 */
public class DrawerItem {
    String itemName;
    int imgResId;

    public DrawerItem(String itemName, int imgResId) {
        super();
        this.itemName = itemName;
        this.imgResId = imgResId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getImgResId() {
        return this.imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }
}
