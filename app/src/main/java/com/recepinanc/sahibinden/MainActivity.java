package com.recepinanc.sahibinden;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.recepinanc.sahibinden.interfaces.OnSwipeTouchListener;
import com.recepinanc.sahibinden.model.BitcoinResponse;
import com.recepinanc.sahibinden.model.Coin;
import com.recepinanc.sahibinden.model.ScoinResponse;
import com.recepinanc.sahibinden.model.User;
import com.recepinanc.sahibinden.service.BitcoinAPI;
import com.recepinanc.sahibinden.service.ScoinAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {

    private final String BITCOIN_API = "https://apiv2.bitcoinaverage.com/indices/";
    private final String SCOIN_API = "https://devakademi.sahibinden.com/";
    private int onPage = 0; // Initially showing SCoin
    private int coinType = Coin.SCOIN;
    private long bitcoinUpdatedAt = 0;
    private long scoinUpdatedAt = 0;
    private double bitcoinValue = 0;
    private double scoinValue = 0;
    private double previousBitcoinValue = 0;
    private double previousScoinValue = 0;

    private TextView coinValueTV;
    private TextView userSuccessTV;
    private ImageView coinIcon;
    private RelativeLayout raiseButton;
    private RelativeLayout fallButton;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        RelativeLayout parentLayout = (RelativeLayout) findViewById(R.id.parent_layout);
        coinValueTV = (TextView) findViewById(R.id.coin_value_tv);
        userSuccessTV = (TextView) findViewById(R.id.success_rate_tv);
        coinIcon = (ImageView) findViewById(R.id.coin_icon);
        raiseButton = (RelativeLayout) findViewById(R.id.raise_button);
        fallButton = (RelativeLayout) findViewById(R.id.fall_button);

        user = getUser();

        updateValues();

        raiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.makeGuess(coinType, true);
            }
        });

        fallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.makeGuess(coinType, false);
            }
        });

        parentLayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeLeft() {
                Log.i("Swipe", "Left");
                onPage = (onPage + 1) % 2;
                coinType = onPage;
                updateViews(coinType);
            }

            @Override
            public void onSwipeRight() {
                Log.i("Swipe", "Right");
                onPage = (onPage + 1) % 2;
                coinType = onPage;
                updateViews(coinType);
            }
        });

        final Handler handler = new Handler();
        final int delay = 60000; //milliseconds

        handler.postDelayed(new Runnable() {
            public void run() {
                Log.i("Handler", "Requested");
                updateValues();
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_statistics:
                Intent intent = new Intent(this, StatisticsActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void updateValues() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SCOIN_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        scoinAPI = retrofit.create(ScoinAPI.class);
        Call<ScoinResponse> scoinResponseCall = scoinAPI.getScoin();
        scoinResponseCall.enqueue(new Callback<ScoinResponse>() {
            @Override
            public void onResponse(@NonNull Call<ScoinResponse> call, @NonNull Response<ScoinResponse> response) {

                double value = response.body().getValue();
                Log.i("Response", "Succeed");
                Log.i("Response", String.valueOf(value));
                previousScoinValue = scoinValue;
                scoinValue = value;
                scoinUpdatedAt = System.currentTimeMillis();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BITCOIN_API)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                bitcoinAPI = retrofit.create(BitcoinAPI.class);
                Call<BitcoinResponse> bitcoinResponseCall = bitcoinAPI.getBitcoin();
                bitcoinResponseCall.enqueue(new Callback<BitcoinResponse>() {
                    @Override
                    public void onResponse(Call<BitcoinResponse> call, Response<BitcoinResponse> response) {

                        double value = response.body().getValue();
                        Log.i("Response", "Succeed");
                        Log.i("Response", String.valueOf(value));
                        previousBitcoinValue = bitcoinValue;
                        bitcoinValue = value;
                        bitcoinUpdatedAt = System.currentTimeMillis();
                        checkUserGuesses();
                        updateViews(coinType);
                    }

                    @Override
                    public void onFailure(Call<BitcoinResponse> call, Throwable t) {
                        Log.i("Retrofit", "Failed");
                        t.printStackTrace();
                    }
                });
            }

            @Override
            public void onFailure(Call<ScoinResponse> call, Throwable t) {
                Log.i("Retrofit", "Failed");
                t.printStackTrace();
            }
        });
    }

    private void updateViews(int page) {
        switch (page) {
            case 0:
                Log.i("Page", String.valueOf(page) + "Scoin");
                Log.i("Time Elapsed:", String.valueOf(System.currentTimeMillis() - scoinUpdatedAt));
                coinIcon.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.scoin));
                coinValueTV.setText(String.valueOf(scoinValue) + "$");
                userSuccessTV.setText(String.valueOf(user.getScoinSuccessPercentage()) + "%");
                break;
            case 1:
                Log.i("Page", String.valueOf(page) + "Bitcoin");
                Log.i("Time Elapsed:", String.valueOf(System.currentTimeMillis() - bitcoinUpdatedAt));
                coinIcon.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.bitcoin));
                coinValueTV.setText(String.valueOf(bitcoinValue) + "$");
                userSuccessTV.setText(String.valueOf(user.getBitcoinSuccessPercentage()) + "%");
                break;
            default:
                Log.w("Page", String.valueOf(page));
        }
    }

    private void checkUserGuesses() {
        Log.i("checkUserGuess", "Checking...");
        if (user.isGuessedScoin() && previousScoinValue - scoinValue != 0) {
            user.checkGuess(Coin.SCOIN, (previousScoinValue - scoinValue > 0));
        }
        if (user.isGuessedBitcoin() && previousScoinValue - scoinValue != 0) {
            user.checkGuess(Coin.BITCOIN, (previousBitcoinValue - bitcoinValue > 0));
        }
        user.setGuessedScoin(false);
        user.setGuessedBitcoin(false);

        Log.i("User", "SCOIN guessed " + user.isGuessedScoin());
        Log.i("User", "BITCOIN  guessed " + user.isGuessedBitcoin());
        Log.i("User", "BITCOIN will raise " + user.isBitcoinRaises() + "");
        Log.i("User", "SCOIN will raise " + user.isScoinRaises() + "");

        getCache().setUser(user);

        Log.i("User", "Cached.");

    }
}
