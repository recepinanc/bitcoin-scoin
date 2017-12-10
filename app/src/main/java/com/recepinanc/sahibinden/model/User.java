package com.recepinanc.sahibinden.model;

import android.util.Log;

/**
 * Created by recepinanc on 10.12.2017.with <3
 */

public class User {
    private boolean guessedBitcoin;
    private boolean guessedScoin;
    private boolean bitcoinRaises;
    private boolean scoinRaises;
    private int totalBitcoinGuessCount;
    private int correctBitcoinGuessCount;
    private int totalSCoinGuessCount;
    private int correctSCoinGuessCount;

    public User() {
        guessedBitcoin = false;
        guessedScoin = false;
        bitcoinRaises = false;
        scoinRaises = false;
    }

    /**
     * @param coinType  type of coin BITCOIN or SCOIN
     * @param willRaise user's guess on status of coin
     */
    public void makeGuess(int coinType, boolean willRaise) {
        switch (coinType) {
            case Coin.BITCOIN:
                if (!guessedBitcoin)
                    totalBitcoinGuessCount++;
                guessedBitcoin = true;
                bitcoinRaises = willRaise;
                Log.i("User", "SCOIN guessed " + isGuessedScoin());
                Log.i("User", "BITCOIN  guessed " + isGuessedBitcoin());
                Log.i("User", "BITCOIN will raise " + willRaise + "");
                Log.i("User", "BITCOIN correct/total guess " + correctBitcoinGuessCount + "/" + totalBitcoinGuessCount);
                break;
            case Coin.SCOIN:
                if (!guessedScoin)
                    totalSCoinGuessCount++;
                guessedScoin = true;
                scoinRaises = willRaise;
                Log.i("User", "SCOIN guessed " + isGuessedScoin());
                Log.i("User", "BITCOIN  guessed " + isGuessedBitcoin());
                Log.i("User", "SCOIN will raise " + willRaise + "");
                Log.i("User", "SCOIN correct/total guess " + correctSCoinGuessCount + "/" + totalSCoinGuessCount);
                break;
            default:
                Log.w("makeGuess", coinType + " raised ? " + willRaise);
        }
    }

    /**
     * @param coinType  type of coin BITCOIN or SCOIN
     * @param didRaised will coin raised or not
     */
    public void checkGuess(int coinType, boolean didRaised) {
        switch (coinType) {
            case Coin.BITCOIN:
                if (bitcoinRaises == didRaised) {
                    correctBitcoinGuessCount++;
                }
                guessedBitcoin = false;
                break;
            case Coin.SCOIN:
                if (scoinRaises == didRaised) {
                    correctSCoinGuessCount++;
                }
                guessedScoin = false;
                break;
            default:
                Log.w("checkGuess", coinType + " raised ? " + didRaised);
        }
    }

    public double getBitcoinSuccessPercentage() {
        if (correctBitcoinGuessCount == 0) {
            return 0.0;
        }
        return Math.round((((double) correctBitcoinGuessCount / totalBitcoinGuessCount) * 100) / 100) * 100;
    }

    public double getScoinSuccessPercentage() {
        if (correctSCoinGuessCount == 0) {
            return 0.0;
        }
        return Math.round((((double) correctSCoinGuessCount / totalSCoinGuessCount) * 100) / 100) * 100;
    }

    public boolean isGuessedBitcoin() {
        return guessedBitcoin;
    }

    public void setGuessedBitcoin(boolean guessedBitcoin) {
        this.guessedBitcoin = guessedBitcoin;
    }

    public boolean isGuessedScoin() {
        return guessedScoin;
    }

    public void setGuessedScoin(boolean guessedScoin) {
        this.guessedScoin = guessedScoin;
    }

    public boolean isBitcoinRaises() {
        return bitcoinRaises;
    }

    public void setBitcoinRaises(boolean bitcoinRaises) {
        this.bitcoinRaises = bitcoinRaises;
    }

    public boolean isScoinRaises() {
        return scoinRaises;
    }

    public void setScoinRaises(boolean scoinRaises) {
        this.scoinRaises = scoinRaises;
    }

    public int getTotalBitcoinGuessCount() {
        return totalBitcoinGuessCount;
    }

    public void setTotalBitcoinGuessCount(int totalBitcoinGuessCount) {
        this.totalBitcoinGuessCount = totalBitcoinGuessCount;
    }

    public int getCorrectBitcoinGuessCount() {
        return correctBitcoinGuessCount;
    }

    public void setCorrectBitcoinGuessCount(int correctBitcoinGuessCount) {
        this.correctBitcoinGuessCount = correctBitcoinGuessCount;
    }

    public int getTotalSCoinGuessCount() {
        return totalSCoinGuessCount;
    }

    public void setTotalSCoinGuessCount(int totalSCoinGuessCount) {
        this.totalSCoinGuessCount = totalSCoinGuessCount;
    }

    public int getCorrectSCoinGuessCount() {
        return correctSCoinGuessCount;
    }

    public void setCorrectSCoinGuessCount(int correctSCoinGuessCount) {
        this.correctSCoinGuessCount = correctSCoinGuessCount;
    }

}
