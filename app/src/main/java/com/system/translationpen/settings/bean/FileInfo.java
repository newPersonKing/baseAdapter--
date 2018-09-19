package com.system.translationpen.settings.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FileInfo  {

    @Id(autoincrement = true)
    private Long id=null;

    private String url; //URL
    private int length; //长度或结束位置
    private int start; //开始位置
    private int now;//当前进度

    @Generated(hash = 2054769199)
    public FileInfo(Long id, String url, int length, int start, int now) {
        this.id = id;
        this.url = url;
        this.length = length;
        this.start = start;
        this.now = now;
    }

    @Generated(hash = 1367591352)
    public FileInfo() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getNow() {
        return now;
    }

    public void setNow(int now) {
        this.now = now;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
