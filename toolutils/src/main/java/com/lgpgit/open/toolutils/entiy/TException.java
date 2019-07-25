package com.lgpgit.open.toolutils.entiy;

import com.lgpgit.open.finaldb.annotation.sqlite.Property;
import com.lgpgit.open.finaldb.annotation.sqlite.Table;
import com.lgpgit.open.finaldb.annotation.sqlite.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * exception实体对象类
 *
 * @author lugp
 * @date 2019/1/30
 */
@Table(name = "T_EXCEPTION")
public class TException extends TBase implements Serializable {

    @Transient
    private static final long serialVersionUID = -807228544878078770L;

    @Property(column = "MESSAGE")
    private String message;
    @Property(column = "TYPE")
    private String type;
    @Property(column = "SAVE_TIME")
    private Date saveTime;
    @Property(column = "DELETE_TIME")
    private Date deleteTime;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}
