/*
 *
 *
 *    Copyright (c) 2018. mrxmgd.com
 *
 *     @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.util;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/*
 *
 *
 *  SharedPreferences工具类
 *   @author MRXMGD <mrxmgd@gmail.com>
 *
 */
public class SharedPreferenceUtils {
    private SharedPreferences sharedPreferences;
    private static SharedPreferenceUtils mSharedPreferencesUtil;

    public static SharedPreferenceUtils getInstance() {
        if (mSharedPreferencesUtil == null) {
            mSharedPreferencesUtil = new SharedPreferenceUtils();
        }
        return mSharedPreferencesUtil;
    }


    /**
     * 获取SP
     *
     * @param context
     * @return
     */
    public SharedPreferenceUtils getSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return this;
    }


    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return sharedPreferences.contains(key);
    }


    /**
     * 保存
     *
     * @param key
     * @param object
     */
    public void putData(String key, Object object) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, (String) object);
        }
        editor.commit();
    }


    /**
     * 获取数据
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public Object getData(String key, Object defaultObject) {
        if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        } else {
            return sharedPreferences.getString(key, (String) defaultObject);
        }
    }

    /**
     * 获取Set<String>
     *
     * @param key
     * @param defValues
     * @return
     */
    public Set<String> getStringSet(String key, Set<String> defValues) {
        return sharedPreferences.getStringSet(key, defValues);
    }

    /**
     * 保存Set<String>集合
     *
     * @param key
     * @param value
     * @return
     */
    public boolean putStringSet(String key, Set<String> value) {
        return sharedPreferences.edit().putStringSet(key, value).commit();
    }


    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }


    /**
     * 删除指定SP
     *
     * @param spName
     */
    public void removeSharedPreferences(String spName) {
        sharedPreferences.edit().remove(spName).commit();
    }


    /**
     * 清空SP
     */
    public void clearSharedPreferences() {
        sharedPreferences.edit().clear().commit();
    }

}
