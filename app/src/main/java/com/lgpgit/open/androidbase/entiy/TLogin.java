package com.lgpgit.open.androidbase.entiy;

import com.lgpgit.open.finaldb.annotation.sqlite.Property;
import com.lgpgit.open.finaldb.annotation.sqlite.Table;
import com.lgpgit.open.finaldb.annotation.sqlite.Transient;
import com.lgpgit.open.toolutils.entiy.TBase;


import java.io.Serializable;
import java.util.Date;

@Table(name = "T_LOGIN")
public class TLogin extends TBase implements Serializable {

    @Transient
    private static final long serialVersionUID = -3843575840548181090L;

    @Property(column = "USER_NAME")
    private String userName;
    @Property(column = "PASS_WORD")
    private String passWord;
    @Property(column = "REGISTER_TIME")
    private Date registerTime;
    @Property(column = "DELETE")
    private Boolean delete;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    @Override
    public void setCode(String code) {
        super.setCode(code);
    }
}
