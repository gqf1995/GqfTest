package lili.com.gqftest;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Administrator on 2017/8/14.
 */

public class ViewType implements MultiItemEntity, Cloneable {

    public static final int edi_type=0;
    public static final int camera_type=1;
    public static final int wap_type=2;
    public static final int src_type=3;
    public static final int lin_type=4;

    public static final int left_type=1;
    public static final int center_type=2;
    public static final int right_type=3;

    int Type;

    String ediTxt;
    String imgpath;
    String src;
    int position;
    int ediTxtShowType=0;

    public int getEdiTxtShowType() {
        return ediTxtShowType;
    }

    public void setEdiTxtShowType(int ediTxtShowType) {
        this.ediTxtShowType = ediTxtShowType;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getEdiTxt() {
        return ediTxt;
    }

    public void setEdiTxt(String ediTxt) {
        this.ediTxt = ediTxt;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public ViewType(final int type) {
        Type = type;
    }

    @Override
    public int getItemType() {
        return Type;
    }

    public ViewType clone() throws CloneNotSupportedException {
        return (ViewType) super.clone();
    }
}
