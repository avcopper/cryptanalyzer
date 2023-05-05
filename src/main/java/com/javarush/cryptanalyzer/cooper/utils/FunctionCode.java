package com.javarush.cryptanalyzer.cooper.utils;

import com.javarush.cryptanalyzer.cooper.services.*;

public enum FunctionCode {
    ENCODE(new Encode()),
    DECODE(new Decode()),
    BRUTE_FORCE(new BruteForce()),
    ANALYSIS(new Analysis()),
    UNSUPPORTED(new UnsupportedFunction());

    private CryptFunction cryptFunction;

    FunctionCode(CryptFunction cryptFunction) {
        this.cryptFunction = cryptFunction;
    }

    public CryptFunction getCryptFunction() {
        return cryptFunction;
    }
}
