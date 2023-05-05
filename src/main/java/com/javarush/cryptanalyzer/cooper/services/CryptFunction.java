package com.javarush.cryptanalyzer.cooper.services;

import java.io.IOException;

public interface CryptFunction {
    public String execute(String[] params) throws IOException;
}
