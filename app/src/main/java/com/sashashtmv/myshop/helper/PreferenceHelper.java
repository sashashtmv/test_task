package com.sashashtmv.myshop.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class PreferenceHelper {
    private static PreferenceHelper instance;

    private Context mContext;

    private SharedPreferences mSharedPreferences;

    public PreferenceHelper() {
    }

    public static PreferenceHelper getInstance(){
        if(instance == null){
            instance = new PreferenceHelper();
        }
    return instance;
    }

    public void init(Context context){
        this.mContext = context;
        this.mSharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    public void putString(String key, String value){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String getString(String key){
        return mSharedPreferences.getString(key, "");
    }

    public void putList(String key, Set<String> list){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putStringSet(key,list);
        editor.apply();
    }

    public Set<String> getList(String key) {
        return mSharedPreferences.getStringSet(key, null);
    }
}
