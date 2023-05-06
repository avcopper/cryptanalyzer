package com.javarush.cryptanalyzer.cooper.services;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.javarush.cryptanalyzer.cooper.constants.Exception;
import com.javarush.cryptanalyzer.cooper.exception.UserException;
import com.javarush.cryptanalyzer.cooper.utils.Caesar;
import com.javarush.cryptanalyzer.cooper.constants.CryptoAlphabet;

public class BruteForce implements CryptFunction {
    @Override
    public String execute(String[] params) throws IOException, UserException {
        Path file = Path.of(params[2]);
        String lines = Files.readString(file);
        String cryptString;
        Caesar caesar = new Caesar();

        for (int offset = 0; offset > CryptoAlphabet.CAESAR_ALPHABET_LENGTH * -1; offset--) {
            caesar.setOffset(offset);
            cryptString = caesar.crypt(lines);

            Pattern aPattern = Pattern.compile("^[А-Я](,?|[а-яё]*) ([а-яё ]*(, )*)*\\.$", Pattern.MULTILINE);
            Matcher aMatcher = aPattern.matcher(cryptString);

            if (aMatcher.find()) return cryptString;
        }

        throw new UserException(Exception.CODE_NOT_FOUND);
    }
}
