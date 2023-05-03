package com.javarush.cryptanalyzer.cooper.app;

import java.util.*;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.StandardOpenOption;
import com.javarush.cryptanalyzer.cooper.constants.CryptoAlphabet;
import com.javarush.cryptanalyzer.cooper.utils.ValueComparator;

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
     * Пытается анализировать частоту вхождений символов из словаря и экстраполировать ее на частоту символов в зашифрованном тексте
     * Получается, конечно же, откровенная дичь даже на гигантских объемах текста (в качестве библиотеки весь роман Братья Карамазовы...)
     * Без ручной доработки текста никак не обойтись...
     * @param fromFile - зашифрованный файл
     * @param dictionary - словарь этого же автора
     * @param toFile - расшифрованный файл
     * @throws IOException
     */
    public static void analyseText(Path fromFile, Path dictionary, Path toFile) throws IOException {
        String encodedLines = Files.readString(fromFile);
        String dictionaryLines = Files.readString(dictionary);

        Map<Character, Integer> sortedInputCharsMap = getAnalysedTextMap(encodedLines);
        Map<Character, Integer> sortedDictionaryCharsMap = getAnalysedTextMap(dictionaryLines);

        List<Character> textKeyList = new ArrayList<>(sortedInputCharsMap.keySet());
        List<Character> dictionaryKeyList = new ArrayList<>(sortedDictionaryCharsMap.keySet());

        StringBuilder decodedLines = new StringBuilder(encodedLines);
        char oldChar, newChar;

        for (int i = 0; i < decodedLines.length(); i++) {
            oldChar = decodedLines.charAt(i);
            int index = textKeyList.indexOf(oldChar);

            if (index == -1 || index >= dictionaryKeyList.size()) continue;

            newChar = dictionaryKeyList.get(index);
            decodedLines.replace(i, i + 1, String.valueOf(newChar));
        }

         Files.writeString(toFile, decodedLines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
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

    /**
     * @param text - анализируемый текст
     * @return - возвращает отсортированный ассоциативный массив с количеством вхождений каждого символа от большего к меньшему
     */
    private static Map<Character, Integer> getAnalysedTextMap(String text) {
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
        return sortedCharsMap;
    }
}
