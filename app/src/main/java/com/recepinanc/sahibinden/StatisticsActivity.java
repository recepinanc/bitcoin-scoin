package com.recepinanc.sahibinden;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.recepinanc.sahibinden.model.User;

/**
 * Created by recepinanc on 10.12.2017.with <3
 */

public class StatisticsActivity extends BaseActivity {

    private User user;
    private TextView bitcoinTotal;
    private TextView bitcoinCorrect;
    private TextView bitcoinRate;
    private TextView scoinTotal;
    private TextView scoinCorrect;
    private TextView scoinRate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_layout);

        user = getUser();
        bitcoinTotal = (TextView) findViewById(R.id.bitcoin_guess_tv);
        bitcoinCorrect = (TextView) findViewById(R.id.bitcoin_correct_guess_tv);
        bitcoinRate = (TextView) findViewById(R.id.bitcoin_success_tv);
        scoinTotal = (TextView) findViewById(R.id.bitcoin_guess_tv);
        scoinCorrect = (TextView) findViewById(R.id.scoin_correct_guess_tv);
        scoinRate = (TextView) findViewById(R.id.scoin_success_tv);

        updateViews();
    }

    private void updateViews() {
        bitcoinTotal.setText(user.getTotalBitcoinGuessCount() + "");
        bitcoinCorrect.setText(user.getCorrectBitcoinGuessCount() + "");
        bitcoinRate.setText(user.getBitcoinSuccessPercentage() + "%");
        scoinTotal.setText(user.getTotalSCoinGuessCount() + "");
        scoinCorrect.setText(user.getCorrectSCoinGuessCount() + "");
        scoinRate.setText(user.getScoinSuccessPercentage() + "%");

    }
}
