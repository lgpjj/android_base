package com.lgpgit.open.toolutils.entiy;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.io.Serializable;

/**
 * sqlite的对象基础类
 *
 * @author lugp
 * @date 2019/1/30
 */
public class TBase implements Serializable {

    @Transient
    private static final long serialVersionUID = -7637442168345777446L;

    @Id(column = "ID")
    private Integer id;

    @Property(column = "CODE")
    private String code;

    public TBase() {
    }

    public TBase(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    public TBase(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
