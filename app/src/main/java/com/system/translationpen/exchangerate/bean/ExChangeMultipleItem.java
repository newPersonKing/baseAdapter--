package com.system.translationpen.exchangerate.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ExChangeMultipleItem implements MultiItemEntity {
    @Id(autoincrement = true)
    private Long id=null;

    public static final int one = 1;
    public static final int two = 0;
    public static final int three = 3;
    private int itemType;
    private String curno;
    private String cname;
    private String ename;
    private String num;
    private String middle;
    private String update;
    private String ssRote;
    private String quyu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getQuyu() {
        return quyu;
    }

    public void setQuyu(String quyu) {
        this.quyu = quyu;
    }

    public String getSsRote() {
        return ssRote;
    }

    public void setSsRote(String ssRote) {
        this.ssRote = ssRote;
    }

    public ExChangeMultipleItem(int itemType) {
         this.itemType=itemType;
    }

    @Generated(hash = 214121088)
    public ExChangeMultipleItem(Long id, int itemType, String curno, String cname,
            String ename, String num, String middle, String update, String ssRote,
            String quyu) {
        this.id = id;
        this.itemType = itemType;
        this.curno = curno;
        this.cname = cname;
        this.ename = ename;
        this.num = num;
        this.middle = middle;
        this.update = update;
        this.ssRote = ssRote;
        this.quyu = quyu;
    }

    @Generated(hash = 9515878)
    public ExChangeMultipleItem() {
    }

    public String getCurno() {
        return curno;
    }

    public void setCurno(String curno) {
        this.curno = curno;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setId(long id) {
        this.id = id;
    }
}
