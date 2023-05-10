package com.javarush.cryptanalyzer.cooper.services;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.InvalidPathException;
import com.javarush.cryptanalyzer.cooper.utils.Caesar;
import com.javarush.cryptanalyzer.cooper.constants.DefaultValues;
import com.javarush.cryptanalyzer.cooper.exception.UserException;
import com.javarush.cryptanalyzer.cooper.constants.CryptoAlphabet;
import com.javarush.cryptanalyzer.cooper.constants.ExceptionConstant;

public class BruteForce implements CryptFunction {
    @Override
    public String execute(String[] params) throws IOException, UserException {
        try {
            if (DefaultValues.EMPTY_STRING.equals(params[2])) throw new UserException(ExceptionConstant.FILE_NOT_SELECTED);

            Path file = Path.of(params[2]);
            String lines = Files.readString(file);
            String cryptString;
            Caesar caesar = new Caesar();

            for (int offset = 0; offset > CryptoAlphabet.CAESAR_ALPHABET_LENGTH * -1; offset--) {
                caesar.setOffset(offset);
                cryptString = caesar.crypt(lines);

                Pattern aPattern = Pattern.compile("^[А-ЯЁ]{1}(,|[а-яё])*(\\s)*( [а-яё]+,?)*([\\.\\?!])$", Pattern.MULTILINE);
                Matcher aMatcher = aPattern.matcher(cryptString);

                if (aMatcher.find()) return cryptString;
            }
        } catch (InvalidPathException ex) {
            throw new UserException(ExceptionConstant.WRONG_FILE_PATH);
        } catch (NumberFormatException ex) {
            throw new UserException(ExceptionConstant.WRONG_KEY);
        }

        throw new UserException(ExceptionConstant.CODE_NOT_FOUND);
    }
}
