package com.recepinanc.sahibinden.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by recepinanc on 10.12.2017.with <3
 */

public class ScoinResponse {

    @SerializedName("value")
    double value;

    public double getValue() {
        return value;
    }
}
