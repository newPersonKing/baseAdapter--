package com.system.translationpen.exchangerate.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FirstPageBean {
    @Id(autoincrement = true)
    private Long id=null;

    private int position;

    private String Curno;

    private String ssRote;

    private String middle;

    @Generated(hash = 1201576732)
    public FirstPageBean(Long id, int position, String Curno, String ssRote,
            String middle) {
        this.id = id;
        this.position = position;
        this.Curno = Curno;
        this.ssRote = ssRote;
        this.middle = middle;
    }

    @Generated(hash = 1568854556)
    public FirstPageBean() {
    };

    public String getSsRote() {
        return ssRote;
    }

    public void setSsRote(String ssRote) {
        this.ssRote = ssRote;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCurno() {
        return Curno;
    }

    public void setCurno(String curno) {
        Curno = curno;
    }
}
