package com.javarush.cryptanalyzer.cooper.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import com.javarush.cryptanalyzer.cooper.utils.Caesar;
import com.javarush.cryptanalyzer.cooper.constants.DefaultFiles;

public class Encode implements CryptFunction {
    @Override
    public String execute(String[] params) throws IOException {
        Path file = Path.of(params[2]);
        Path encodedFile = Path.of(DefaultFiles.ENCODED_FILE);

        String lines = Files.readString(file);
        Caesar caesar = new Caesar(Integer.parseInt(params[1]));
        String cryptString = caesar.crypt(lines);
        Path result = Files.writeString(encodedFile, cryptString, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        return "";
    }
}
