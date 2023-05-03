package com.javarush.cryptanalyzer.cooper.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileFilter;
import com.javarush.cryptanalyzer.cooper.app.Caesar;
import com.javarush.cryptanalyzer.cooper.constants.AppWindow;

public class WindowListener implements ActionListener {
    Window frame;

    public WindowListener(Window frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case AppWindow.FILE_ENCRYPT_DECRYPT:
                selectFile(AppWindow.TYPE_FILE);
                break;
            case AppWindow.FILE_DICTIONARY:
                selectFile(AppWindow.TYPE_DICTIONARY);
                break;
            case AppWindow.ENCRYPT:
                try {
                    encryptText();
                } catch (Exception ex) {
                    showError(ex.getMessage());
                }
                break;
            case AppWindow.DECRYPT:
                try {
                    decryptText();
                } catch (Exception ex) {
                    showError(ex.getMessage());
                }
                break;
            case AppWindow.BRUTE_FORCE:
                try {
                    makePain();
                } catch (Exception ex) {
                    showError(ex.getMessage());
                }
                break;
            case AppWindow.ANALYSIS:
                try {
                    tryToDoSomethingStupid();
                } catch (IOException ex) {
                    showError(ex.getMessage());
                }
                break;
            case AppWindow.OPEN_FILE:
                try {
                    Desktop.getDesktop().open(frame.getResultFile().toFile());
                } catch (IOException ex) {
                    showError(ex.getMessage());
                }
                break;
            case AppWindow.OPEN_DIR:
                try {
                    Desktop.getDesktop().open(frame.getResultFile().toAbsolutePath().getParent().toFile());
                } catch (IOException ex) {
                    showError(ex.getMessage());
                }
                break;
            case "About":
                showError("Unknown shit-developer )");
                break;
            case "Exit":
                System.exit(0);
                break;
        }
    }

    /**
     * Выбор файла на диске
     * @param fileType - тип файла (исходный файл/файл для аналитики)
     */
    private void selectFile(String fileType) {
        frame.clearResult();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выбор файла");
        fileChooser.setCurrentDirectory(new File("C:\\"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getName().endsWith("txt");
            }
            @Override
            public String getDescription() {
                return "Текстовые файлы (*.txt)";
            }
        });
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().isFile()) {
            if (fileType.equals(AppWindow.TYPE_FILE))
                frame.setFilePath(fileChooser.getSelectedFile().getPath());
            else if (fileType.equals(AppWindow.TYPE_DICTIONARY))
                frame.setAnalysisFilePath(fileChooser.getSelectedFile().getPath());
        }
    }

    /**
     * Шифрование текста
     * @throws IOException
     */
    private void encryptText() throws IOException {
        frame.clearResult();

        Path file = frame.getFilePath(AppWindow.TYPE_FILE);
        Path encodedFile = Path.of(AppWindow.DEFAULT_ENCODED_FILE);
        Caesar caesar = new Caesar(frame.getKey());
        caesar.cryptTextToFile(file, encodedFile);
        frame.showResult(encodedFile, caesar.getOffset(), AppWindow.APP_RESULT_ENCRYPT);
    }

    /**
     * Расшифровка текста
     * @throws IOException
     */
    private void decryptText() throws IOException {
        frame.clearResult();

        Path file = frame.getFilePath(AppWindow.TYPE_FILE);
        Path decodedFile = Path.of(AppWindow.DEFAULT_DECODED_FILE);
        Caesar caesar = new Caesar(frame.getKey() * -1);
        caesar.cryptTextToFile(file, decodedFile);
        frame.showResult(decodedFile, caesar.getOffset(), AppWindow.APP_RESULT_DECRYPT);
    }

    /**
     * Попытка расшифровки перебором сдвига
     * @throws IOException
     */
    private void makePain() throws IOException {
        frame.clearResult();

        Path file = frame.getFilePath(AppWindow.TYPE_FILE);
        Path decodedFile = Path.of(AppWindow.DEFAULT_DECODED_FILE);
        Caesar caesar = new Caesar();
        caesar.bruteForceCryptTextToFile(file, decodedFile);
        frame.showResult(decodedFile, caesar.getOffset(), AppWindow.APP_RESULT_DECRYPT);
    }

    private void tryToDoSomethingStupid() throws IOException {
        frame.clearResult();

        Path file = frame.getFilePath(AppWindow.TYPE_FILE);
        Path dictionary = frame.getFilePath(AppWindow.TYPE_DICTIONARY);
        Path decodedFile = Path.of(AppWindow.DEFAULT_DECODED_FILE);
        Caesar.analyseText(file, dictionary, decodedFile);
        frame.showResult(decodedFile, 0, AppWindow.APP_RESULT_ANALYSE);
    }

    /**
     * Показ ошибки
     * @param message - сообщение для показа
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
