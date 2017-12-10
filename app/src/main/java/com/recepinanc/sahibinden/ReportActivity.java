package com.recepinanc.sahibinden;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.recepinanc.sahibinden.model.User;

/**
 * Created by recepinanc on 10.12.2017.with <3
 */

public class ReportActivity extends BaseActivity {

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = getUser();
    }
}
