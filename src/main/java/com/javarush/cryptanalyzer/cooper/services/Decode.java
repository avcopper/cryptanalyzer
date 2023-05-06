package com.javarush.cryptanalyzer.cooper.services;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;

import com.javarush.cryptanalyzer.cooper.constants.Exception;
import com.javarush.cryptanalyzer.cooper.exception.UserException;
import com.javarush.cryptanalyzer.cooper.utils.Caesar;

public class Decode implements CryptFunction {
    @Override
    public String execute(String[] params) throws IOException {
        try {
            if ("".equals(params[2])) throw new UserException(Exception.FILE_NOT_SELECTED);

            Path file = Path.of(params[2]);
            String lines = Files.readString(file);
            Caesar caesar = new Caesar(Integer.parseInt(params[1]) * -1);
            return caesar.crypt(lines);
        } catch (InvalidPathException ex) {
            throw new UserException(Exception.WRONG_FILE_PATH);
        } catch (NumberFormatException ex) {
            throw new UserException(Exception.WRONG_KEY);
        }
    }
}
