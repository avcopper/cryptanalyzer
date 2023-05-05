package com.javarush.cryptanalyzer.cooper.services;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.StandardOpenOption;
import com.javarush.cryptanalyzer.cooper.utils.Caesar;
import com.javarush.cryptanalyzer.cooper.constants.DefaultFiles;
import com.javarush.cryptanalyzer.cooper.constants.CryptoAlphabet;

public class BruteForce implements CryptFunction {
    @Override
    public String execute(String[] params) throws IOException {
        Path file = Path.of(params[2]);
        Path decodedFile = Path.of(DefaultFiles.DECODED_FILE);

        String lines = Files.readString(file);
        String cryptString;
        Caesar caesar = new Caesar();

        for (int offset = 0; offset > CryptoAlphabet.CAESAR_ALPHABET_LENGTH * -1; offset--) {
            caesar.setOffset(offset);
            cryptString = caesar.crypt(lines);
            Pattern aPattern = Pattern.compile("^[А-Я](,?|[а-яё]*) ([а-яё ]*(, )*)*\\.$", Pattern.MULTILINE);
            //Pattern aPattern = Pattern.compile("[А-Яа-яЁё]*(, |\\.){1}[А-Яа-яЁё ]*");
            Matcher aMatcher = aPattern.matcher(cryptString);
            if (aMatcher.find()) {
                System.out.println(offset);
                //System.out.println(cryptString);
                Path result = Files.writeString(decodedFile, cryptString, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            }
        }

        return "";
    }
}
