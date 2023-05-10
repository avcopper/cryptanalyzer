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

    /**
     * Запуск интерфейса программы
     */
    public void run() {
        controller.getView().start();
    }

    /**
     * @param params - параметры работы программы
     * @return - возвращает результат работы криптографической программы
     * @throws IOException - выбрасывает исключение, связанное с выбранными файлами
     */
    public static String crypt(String[] params) throws IOException {
        CryptFunction cryptFunction = getCryptFunction(params[0]);
        return cryptFunction.execute(params);
    }

    /**
     * @param mode - режим работы
     * @return - возвращает функцию шифрования
     */
    private static CryptFunction getCryptFunction(String mode) {
        return switch (mode) {
            case "1" -> FunctionCode.valueOf("ENCODE").getCryptFunction();
            case "2" -> FunctionCode.valueOf("DECODE").getCryptFunction();
            case "3" -> FunctionCode.valueOf("BRUTE_FORCE").getCryptFunction();
            case "4" -> FunctionCode.valueOf("ANALYSIS").getCryptFunction();
            default -> FunctionCode.valueOf("UNSUPPORTED").getCryptFunction();
        };
    }

    /**
     * Показывает результат работы пользователю
     * @param result - результат работы программы
     */
    public void showResult(Result result) {
        controller.getView().showResult(result);
    }
}
