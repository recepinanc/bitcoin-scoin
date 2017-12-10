package com.recepinanc.sahibinden.service;

import com.recepinanc.sahibinden.model.BitcoinResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by recepinanc on 10.12.2017.with <3
 */

public interface BitcoinAPI {
    @GET("global/ticker/BTCUSD")
    Call<BitcoinResponse> getBitcoin();

}
