package com.javarush.cryptanalyzer.cooper.utils;

import java.util.*;
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

    /**
     * @param text - анализируемый текст
     * @return - возвращает список символов отсортированный по убыванию частоты использования в тексте
     */
    public static List<Character> getAnalysedSymbolList(String text) {
        Map<Character, Integer> countCharsMap = new HashMap<>();
        ValueComparator valueComparator = new ValueComparator(countCharsMap);
        Map<Character, Integer> sortedCharsMap = new TreeMap<>(valueComparator);

        for (int i = 0; i < text.length(); i++) {
            Character currentChar = text.charAt(i);

            if (CryptoAlphabet.CAESAR_ALPHABET.indexOf(currentChar) != -1) {
                int currentCount = countCharsMap.get(currentChar) != null ? countCharsMap.get(currentChar) : 0;
                countCharsMap.put(currentChar, currentCount + 1);
            }
        }

        sortedCharsMap.putAll(countCharsMap);
        return new ArrayList<>(sortedCharsMap.keySet());
    }

    /**
     * @return - возвращает сгенерированный ключ шифрования
     */
    public static String generateKey() {
        int random = (int) (1 + Math.random() * (CryptoAlphabet.CAESAR_ALPHABET_LENGTH - 1));
        return String.valueOf(random);
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
