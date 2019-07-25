package com.lgpgit.open.toolutils.database;

import android.content.Context;

import com.lgpgit.open.finaldb.FinalDb;

import java.util.List;

/**
 * sqlite操作类,缓存
 * @author lugp
 * @date 2019/01/30
 */
public class Database {

	private static FinalDb db;

    private static Database dao;

    /**
     * 获取操作对象（单例）
     * @param context
     * @return
     */
    public static synchronized Database getDatabase(Context context, String dbName) {
        if (db == null) {
            db = FinalDb.create(context, dbName, true);
        }
        if (dao == null) {
            dao = new Database();
        }
        return dao;
    }

    //删除数据库
    public void dropDb() {
        db.dropDb();
    }

    //根据字段名的值获取全部数据
    public <T> List<T> getInfoByKey(Class<T> t, String keyName, String key) {
        return db.findAllByWhere(t, keyName + "=" + " '" + key + "'");
    }

    //根据主键查询数据
    public <T> T getInfoById(Class<T> t, String id) {
        return db.findById(id, t);
    }

    //保存数据
    public <T> void saveInfo(T t) {
        db.save(t);
    }

    //保存多条数据
    public <T> void saveInfoList(List<T> t) {
        for (int i = 0; i < t.size(); i++) {
            saveInfo(t.get(i));
        }
    }

    //更新数据
    public <T> void updateInfo(T t) {
        db.update(t);
    }

    //删除数据
    public <T> void deleteInfo(T t) {
        db.delete(t);
    }

    public <T> void deleteInfoById(Class<T> t, String id) {
        db.deleteById(t, id);
    }

    public <T> void deleteInfoByKey(Class<T> t, String keyName, String key) {
        db.deleteByWhere(t, keyName + "=" + " '" + key + "'");
    }

    //删除多条数据
    public <T> void deleteInfoList(List<T> t) {
        for (int i = 0; i < t.size(); i++) {
            deleteInfo(t.get(i));
        }
    }
}
