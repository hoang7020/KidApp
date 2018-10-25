package vn.edu.fpt.kidapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtil {

    private static SharedPreferences pref;
    private static PreferenceUtil _instance;

    public synchronized static PreferenceUtil getInstance(Context context) {

        if(_instance == null){
            _instance = new PreferenceUtil();
        }
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return _instance;
    }

    public void putStringValue(String KEY, String value) {
        pref.edit().putString(KEY, value).apply();
    }

    public String getStringValue(String KEY, String defvalue) {
        return pref.getString(KEY, defvalue);
    }
}
