package com.javarush.cryptanalyzer.cooper.services;

import com.javarush.cryptanalyzer.cooper.exception.UserException;

import java.io.IOException;

public interface CryptFunction {
    String execute(String[] params) throws IOException, UserException;
}
