package com.javarush.cryptanalyzer.cooper.app;

import java.io.IOException;

import com.javarush.cryptanalyzer.cooper.entity.Result;
import com.javarush.cryptanalyzer.cooper.utils.FunctionCode;
import com.javarush.cryptanalyzer.cooper.controller.Controller;
import com.javarush.cryptanalyzer.cooper.services.CryptFunction;

public class Application {
    private  final Controller controller;

    public Application(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        controller.getView().start();
    }

    public static String crypt(String[] params) throws IOException {
        CryptFunction cryptFunction = getCryptFunction(params[0]);
        return cryptFunction.execute(params);
    }

    private static CryptFunction getCryptFunction(String mode) {
        return switch (mode) {
            case "1" -> FunctionCode.valueOf("ENCODE").getCryptFunction();
            case "2" -> FunctionCode.valueOf("DECODE").getCryptFunction();
            case "3" -> FunctionCode.valueOf("BRUTE_FORCE").getCryptFunction();
            case "4" -> FunctionCode.valueOf("ANALYSIS").getCryptFunction();
            default -> FunctionCode.valueOf("UNSUPPORTED").getCryptFunction();
        };
    }

    public void showResult(Result result) {
        controller.getView().showResult(result);
    }
}
