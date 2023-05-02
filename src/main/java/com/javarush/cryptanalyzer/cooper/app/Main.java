package com.javarush.cryptanalyzer.cooper.app;

import com.javarush.cryptanalyzer.cooper.view.Window;
import com.javarush.cryptanalyzer.cooper.constants.AppWindow;
import com.javarush.cryptanalyzer.cooper.constants.CryptoAlphabet;

public class Main {
    public static void main(String[] args) {
        System.out.println(CryptoAlphabet.CAESAR_ALPHABET_LENGTH);
        Window window = new Window(AppWindow.DEFAULT_WIDTH, AppWindow.DEFAULT_HEIGHT);
        window.setVisible(true);
    }
}
