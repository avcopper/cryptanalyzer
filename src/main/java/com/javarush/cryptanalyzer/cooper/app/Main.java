package com.javarush.cryptanalyzer.cooper.app;

import com.javarush.cryptanalyzer.cooper.view.Window;
import com.javarush.cryptanalyzer.cooper.constants.AppWindow;

public class Main {
    public static void main(String[] args) {
        Window window = new Window(AppWindow.DEFAULT_WIDTH, AppWindow.DEFAULT_HEIGHT);
        window.setVisible(true);
    }
}
