package com.javarush.cryptanalyzer.cooper.services;

import java.nio.file.InvalidPathException;
import java.util.List;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;

import com.javarush.cryptanalyzer.cooper.constants.Exception;
import com.javarush.cryptanalyzer.cooper.exception.UserException;
import com.javarush.cryptanalyzer.cooper.utils.Caesar;

public class Analysis implements CryptFunction {
    @Override
    public String execute(String[] params) throws IOException {
        try {
            if ("".equals(params[2])) throw new UserException(Exception.FILE_NOT_SELECTED);

            Path file = Path.of(params[2]);
            String encodedLines = Files.readString(file);
            List<Character> textKeyList = Caesar.getAnalysedSymbolList(encodedLines);

            Path dictionary = Path.of(params[3]);
            String dictionaryLines = Files.readString(dictionary);
            List<Character> dictionaryKeyList = Caesar.getAnalysedSymbolList(dictionaryLines);

            StringBuilder decodedLines = new StringBuilder(encodedLines);
            char oldChar, newChar;

            for (int i = 0; i < decodedLines.length(); i++) {
                oldChar = decodedLines.charAt(i);
                int index = textKeyList.indexOf(oldChar);

                if (index == -1 || index >= dictionaryKeyList.size()) continue;

                newChar = dictionaryKeyList.get(index);
                decodedLines.replace(i, i + 1, String.valueOf(newChar));
            }

            return decodedLines.toString();
        } catch (InvalidPathException ex) {
            throw new UserException(Exception.WRONG_FILE_PATH);
        } catch (NumberFormatException ex) {
            throw new UserException(Exception.WRONG_KEY);
        }
    }
}
