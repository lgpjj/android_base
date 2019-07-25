package com.lgpgit.open.androidbase.entiy;

import com.lgpgit.open.finaldb.annotation.sqlite.Transient;
import com.lgpgit.open.toolutils.entiy.TBase;

import java.io.Serializable;

/**
 * @author lugp
 * @date 2019/2/15
 */
public class TExFile extends TBase implements Serializable {

    @Transient
    private static final long serialVersionUID = -3114446334280383792L;
}
