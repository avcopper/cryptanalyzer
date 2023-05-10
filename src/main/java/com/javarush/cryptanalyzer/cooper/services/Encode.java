package com.javarush.cryptanalyzer.cooper.services;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import com.javarush.cryptanalyzer.cooper.utils.Caesar;
import com.javarush.cryptanalyzer.cooper.constants.DefaultValues;
import com.javarush.cryptanalyzer.cooper.exception.UserException;
import com.javarush.cryptanalyzer.cooper.constants.ExceptionConstant;

public class Encode implements CryptFunction {
    @Override
    public String execute(String[] params) throws IOException {
        try {
            if (DefaultValues.EMPTY_STRING.equals(params[2])) throw new UserException(ExceptionConstant.FILE_NOT_SELECTED);

            Path file = Path.of(params[2]);
            String lines = Files.readString(file);
            Caesar caesar = new Caesar(Integer.parseInt(params[1]));
            return caesar.crypt(lines);
        } catch (InvalidPathException ex) {
            throw new UserException(ExceptionConstant.WRONG_FILE_PATH);
        } catch (NumberFormatException ex) {
            throw new UserException(ExceptionConstant.WRONG_KEY);
        }

    }
}
