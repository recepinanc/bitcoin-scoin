package com.recepinanc.sahibinden.model;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by recepinanc on 10.12.2017.with <3
 */

public class ObjectCache {

    private static final String USER = "USER";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public ObjectCache(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        this.sharedPreferences = sharedPreferences;
        this.editor = editor;
    }

    public User getUser() {
        Log.i("User", "getUser");
        Gson gson = new Gson();
        String userJSON = sharedPreferences.getString(USER, null);
        User user = null;
        if (userJSON != null) {
            user = gson.fromJson(userJSON, User.class);
        }
        return user;
    }

    public void setUser(User user) {
        Log.i("user", "setUser");
        Gson gson = new Gson();
        String userJSONString = gson.toJson(user);
        editor.putString(USER, userJSONString);
        editor.apply();
    }

    public void clearCache() {
        setUser(null);
    }
}
