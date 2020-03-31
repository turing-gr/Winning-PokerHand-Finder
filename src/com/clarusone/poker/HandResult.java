package com.clarusone.poker;

public enum HandResult {
    TIE(0),
    WIN(1),
    LOSS(-1);

    public final int comparatorValue;

    HandResult(int comparatorValue) {
        this.comparatorValue = comparatorValue;
    }
}
