package com.javarush.cryptanalyzer.cooper.controller;

import com.javarush.cryptanalyzer.cooper.view.View;

public class Controller {
    private final View view;

    public Controller(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }
}
