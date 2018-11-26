package com.hqj.account.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;


/**
 * Created by hqj on 2018/1/24 0024.
 */

public class SharedHelper {

    private static final String sharedName = "datas";

    //保存数据通用方法
    public static void set(Map<String, String> map, Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for(Map.Entry<String, String> entity : map.entrySet()) {
            editor.putString(entity.getKey(), entity.getValue());
        }
        editor.commit();
    }

    //保存数据通用方法
    public static void set(String key, String value, Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
        Log.i("SharedPreferences：", "写入" + key + "=" + value + "成功");
    }

    public static String get(String key, Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        String value = sp.getString(key, "");
        return value == null ? "" : value;
    }

    public static void remove(String key, Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }
}