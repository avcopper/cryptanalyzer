package com.javarush.cryptanalyzer.cooper.services;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import com.javarush.cryptanalyzer.cooper.utils.Caesar;

public class Encode implements CryptFunction {
    @Override
    public String execute(String[] params) throws IOException {
        Path file = Path.of(params[2]);
        String lines = Files.readString(file);
        Caesar caesar = new Caesar(Integer.parseInt(params[1]));
        return caesar.crypt(lines);
    }
}
