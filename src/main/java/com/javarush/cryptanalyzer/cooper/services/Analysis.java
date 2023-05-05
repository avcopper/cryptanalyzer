package com.javarush.cryptanalyzer.cooper.services;

import java.util.List;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import com.javarush.cryptanalyzer.cooper.utils.Caesar;
import com.javarush.cryptanalyzer.cooper.constants.DefaultFiles;

public class Analysis implements CryptFunction {
    @Override
    public String execute(String[] params) throws IOException {
        Path file = Path.of(params[2]);
        String encodedLines = Files.readString(file);
        List<Character> textKeyList = Caesar.getAnalysedSymbolList(encodedLines);

        Path dictionary = Path.of(params[3]);
        String dictionaryLines = Files.readString(dictionary);
        List<Character> dictionaryKeyList = Caesar.getAnalysedSymbolList(dictionaryLines);

        Path decodedFile = Path.of(DefaultFiles.DECODED_FILE);

        StringBuilder decodedLines = new StringBuilder(encodedLines);
        char oldChar, newChar;

        for (int i = 0; i < decodedLines.length(); i++) {
            oldChar = decodedLines.charAt(i);
            int index = textKeyList.indexOf(oldChar);

            if (index == -1 || index >= dictionaryKeyList.size()) continue;

            newChar = dictionaryKeyList.get(index);
            decodedLines.replace(i, i + 1, String.valueOf(newChar));
        }

        Path result = Files.writeString(decodedFile, decodedLines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        return "";
    }
}
