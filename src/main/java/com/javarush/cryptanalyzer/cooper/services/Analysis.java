package com.javarush.cryptanalyzer.cooper.services;

import java.util.List;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import com.javarush.cryptanalyzer.cooper.utils.Caesar;
import com.javarush.cryptanalyzer.cooper.constants.DefaultValues;
import com.javarush.cryptanalyzer.cooper.exception.UserException;
import com.javarush.cryptanalyzer.cooper.constants.ExceptionConstant;

public class Analysis implements CryptFunction {
    @Override
    public String execute(String[] params) throws IOException {
        try {
            if (DefaultValues.EMPTY_STRING.equals(params[2])) throw new UserException(ExceptionConstant.FILE_NOT_SELECTED);

            Path file = Path.of(params[2]);
            String encodedLines = Files.readString(file).toLowerCase();
            List<Character> textKeyList = Caesar.getAnalysedSymbolList(encodedLines);

            Path dictionary = Path.of(params[3]);
            String dictionaryLines = Files.readString(dictionary).toLowerCase();
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
            throw new UserException(ExceptionConstant.WRONG_FILE_PATH);
        } catch (NumberFormatException ex) {
            throw new UserException(ExceptionConstant.WRONG_KEY);
        }
    }

    /**
     * @param text - исходный текст
     * @param firstCharFrom - искомый символ
     * @param firstCharTo - конечный символ
     * @return - возвращает текст с символами, поменянными местами
     */
    public static String changeSymbolPairs(String text, char firstCharFrom, char firstCharTo) {
        return text
            .replace(firstCharFrom, '$')
            .replace(firstCharTo, firstCharFrom)
            .replace('$', firstCharTo);
    }
}
