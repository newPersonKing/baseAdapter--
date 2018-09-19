package com.system.translationpen.settings.bean;

import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class WifiConnect {
    @Id(autoincrement = true)
    private Long id=null;

    private String name;
    private String password;

    @Generated(hash = 1012277935)
    public WifiConnect(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @Generated(hash = 437061495)
    public WifiConnect() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
