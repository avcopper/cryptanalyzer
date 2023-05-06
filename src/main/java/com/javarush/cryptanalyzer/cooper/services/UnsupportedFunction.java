package com.javarush.cryptanalyzer.cooper.services;

import com.javarush.cryptanalyzer.cooper.constants.Exception;
import com.javarush.cryptanalyzer.cooper.exception.UserException;

public class UnsupportedFunction implements CryptFunction {
    @Override
    public String execute(String[] params) {
        throw new UserException(Exception.UNSUPPORTED_OPERATION);
    }
}
