package com.javarush.cryptanalyzer.cooper.controller;

import com.javarush.cryptanalyzer.cooper.view.View;

public class Controller {
    private final View view;

    public Controller(View view) {
        this.view = view;
    }

    /**
     * @return - возвращает объект интерфейса
     */
    public View getView() {
        return view;
    }
}
