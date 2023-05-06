package com.javarush.cryptanalyzer.cooper.constants;

public class AppWindow {
    public static final int DEFAULT_WIDTH = 500;
    public static final int DEFAULT_HEIGHT = 470;

    public static final String APP_NAME = "Crypto";
    public static final String DEVELOPER = "Developer";
    public static final String DEVELOPER_NAME = "Andrew Cooper";
    public static final String APP_MESSAGE =
            "<html>" +
                "<div style=\"padding: 2px; border: 1px solid black;\">" +
                    "<ol style=\"margin: 0 0 0 15px;\">" +
                        "<li>Выберите файл для шифрования/расшифровки, либо воспользуйтесь файлом по умолчанию</li>" +
                        "<li>Выберите файл словаря, либо воспользуйтесь файлом по умолчанию (для режима анализа)</li>" +
                        "<li>Введите ключ шифрования</li>" +
                    "</ol>" +
                "</div>" +
            "</html>";
    public static final String APP_RESULT_ENCRYPT = "File encoded. Key ";
    public static final String APP_RESULT_DECRYPT = "File decoded. Key ";
    public static final String APP_RESULT_ANALYSE = "File decoded.";

    public static final String ENCRYPT = "Encrypt";
    public static final String DECRYPT = "Decrypt";
    public static final String BRUTE_FORCE = "Brute Force";
    public static final String ANALYSIS = "Analysis";

    public static final String FILE = "File";
    public static final String HELP = "Help";
    public static final String ABOUT = "About";
    public static final String EXIT = "Exit";

    public static final String KEY = "Key";
    public static final String FILE_ENCRYPT_DECRYPT = "File for encrypt/decrypt";
    public static final String FILE_DICTIONARY = "Dictionary file";
    public static final String OPEN_FILE = "Open file";
    public static final String OPEN_DIR = "Open dir";

    public static final String SELECT_FILE = "Select file";
    public static final String TEXT_FILES = "Text files (*.txt)";
}
