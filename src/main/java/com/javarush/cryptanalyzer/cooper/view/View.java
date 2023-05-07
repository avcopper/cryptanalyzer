package com.javarush.cryptanalyzer.cooper.view;

import com.javarush.cryptanalyzer.cooper.entity.Result;

public interface View {
    void start();

    void showResult(Result result);

    void clearResult();
}
