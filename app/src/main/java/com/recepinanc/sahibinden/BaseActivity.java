package com.recepinanc.sahibinden;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.recepinanc.sahibinden.model.ObjectCache;
import com.recepinanc.sahibinden.model.User;
import com.recepinanc.sahibinden.service.BitcoinAPI;
import com.recepinanc.sahibinden.service.ScoinAPI;

import retrofit2.Retrofit;

/**
 * Created by recepinanc on 10.12.2017.with <3
 */

public class BaseActivity extends AppCompatActivity {

    private ObjectCache cache;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    protected Retrofit retrofit;
    protected BitcoinAPI bitcoinAPI;
    protected ScoinAPI scoinAPI;
    protected User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();
        cache = new ObjectCache(getSharedPreferences(), getSharedPreferencesEditor());
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public SharedPreferences.Editor getSharedPreferencesEditor() {
        return editor;
    }

    public ObjectCache getCache() {
        return cache;
    }

    public User getUser() {
        if (cache.getUser() != null) {
            Log.i("User", "Loaded from cached");
            return cache.getUser();
        } else {
            Log.i("User", "Created.");
            return new User();
        }
    }
}
