package com.javarush.cryptanalyzer.cooper.app;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.StandardOpenOption;
import com.javarush.cryptanalyzer.cooper.constants.CryptoAlphabet;

public class Caesar {
    private int offset;

    public Caesar() {
        offset = 0;
    }

    public Caesar(int offset) {
        setOffset(offset);
    }

    /**
     * Сохраняет зашифрованный текст из файла в другой файл
     * @param fromFile - исходный файл
     * @param toFile - целевой файл
     * @return - возвращает путь к целевому файлу
     * @throws IOException
     */
    public Path cryptTextToFile(Path fromFile, Path toFile) throws IOException {
        String lines = Files.readString(fromFile);
        String cryptString = crypt(lines);
        return Files.writeString(toFile, cryptString, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Подбирает сдвиг для расшифровки файла и записывает результат в файл
     * @param fromFile - исходный файл
     * @param toFile - целевой файл
     * @return - возвращает путь к целевому файлу
     * @throws IOException
     */
    public Path bruteForceCryptTextToFile(Path fromFile, Path toFile) throws IOException {
        String lines = Files.readString(fromFile);
        String cryptString;

        for (int offset = 0; offset > CryptoAlphabet.CAESAR_ALPHABET_LENGTH * -1; offset--) {
            setOffset(offset);
            cryptString = crypt(lines);

            Pattern aPattern = Pattern.compile("[А-Яа-яЁё ]*(, ){1}[А-Яа-яЁё ]*(\\. ){1}");
            Matcher aMatcher = aPattern.matcher(cryptString);

            if (aMatcher.find()) {
                return Files.writeString(toFile, cryptString, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        }

        return null;
    }

    /**
     * @param text - исходный текст
     * @return - возвращает зашифрованный/расшифрованный текст
     */
    public String crypt(String text) {
        char[] chars = text.toCharArray();
        StringBuilder resultString = new StringBuilder();
        char newChar;

        for (char ch : chars) {
            int index = getCurrentIndex(ch);

            if (index != -1) {
                int newIndex = getNewIndex(index);
                newChar = getNewChar(newIndex);
                resultString.append(newChar);
            }
            else resultString.append(ch);
        }

        return resultString.toString();
    }

    /**
     * @param c - символ
     * @return - возвращает индекс символа в наборе символов
     */
    private int getCurrentIndex(char c) {
        return CryptoAlphabet.CAESAR_ALPHABET.indexOf(c);
    }

    /**
     * @param index - исходный индекс
     * @return - возвращает новый индекс символа с учетом сдвига
     */
    public int getNewIndex(int index) {
        int newIndex = index + offset;

        if (newIndex >= CryptoAlphabet.CAESAR_ALPHABET_LENGTH) {
            return newIndex % CryptoAlphabet.CAESAR_ALPHABET_LENGTH;
        }
        else if (newIndex < 0) {
            return CryptoAlphabet.CAESAR_ALPHABET_LENGTH + (newIndex % CryptoAlphabet.CAESAR_ALPHABET_LENGTH);
        }
        else {
            return newIndex;
        }
    }

    /**
     * @param cryptIndex - индекс символа
     * @return - возвращает новый символ
     */
    private char getNewChar(int cryptIndex) {
        return CryptoAlphabet.CAESAR_ALPHABET.charAt(cryptIndex);
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset =
            Math.abs(offset) < CryptoAlphabet.CAESAR_ALPHABET_LENGTH ?
                offset :
                offset % CryptoAlphabet.CAESAR_ALPHABET_LENGTH;
    }
}
