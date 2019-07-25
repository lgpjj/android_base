package com.lgpgit.open.toolutils.entiy;

import com.lgpgit.open.finaldb.annotation.sqlite.Id;
import com.lgpgit.open.finaldb.annotation.sqlite.Property;

/**
 * sqlite的对象基础类
 *
 * @author lugp
 * @date 2019/1/30
 */
public class TBase {

    @Id(column = "ID")
    private Integer id;

    @Property(column = "CODE")
    private String code;

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
