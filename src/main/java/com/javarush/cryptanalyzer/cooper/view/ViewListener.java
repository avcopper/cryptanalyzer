package com.javarush.cryptanalyzer.cooper.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.Path;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.javarush.cryptanalyzer.cooper.constants.*;
import com.javarush.cryptanalyzer.cooper.entity.Result;
import com.javarush.cryptanalyzer.cooper.app.Application;
import com.javarush.cryptanalyzer.cooper.exception.UserException;
import com.javarush.cryptanalyzer.cooper.services.Analysis;
import com.javarush.cryptanalyzer.cooper.utils.Caesar;
import com.javarush.cryptanalyzer.cooper.utils.ResultCode;

public class ViewListener implements ActionListener, KeyListener {
    GUIView frame;

    public ViewListener(GUIView frame) {
        this.frame = frame;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        JTextField textField = (JTextField) e.getSource();
        String sourceText = textField.getText();

        if (sourceText.length() > 0) {
            if (sourceText.length() > 1) textField.setText(String.valueOf(sourceText.charAt(0)));
            if (CryptoAlphabet.CAESAR_ALPHABET.indexOf(sourceText.charAt(0)) == -1) textField.setText(DefaultValues.EMPTY_STRING);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case AppWindow.FILE_ENCRYPT:
                frame.clearResult();
                frame.setEncryptFileName(frame.selectFile());
                break;
            case AppWindow.FILE_DICTIONARY:
                frame.clearResult();
                frame.setDictionaryFileName(frame.selectFile());
                break;
            case AppWindow.ENCRYPT:
                encode();
                break;
            case AppWindow.DECRYPT:
                decode();
                break;
            case AppWindow.BRUTE_FORCE:
                bruteForce();
                break;
            case AppWindow.ANALYSIS:
                try {
                    frame.showAnalyticDialog(analyse());
                } catch (IOException ex) {
                    frame.showResult(new Result(ResultCode.ERROR, ex));
                }
                break;
            case AppWindow.OPEN_FILE:
                try {
                    Desktop.getDesktop().open(Path.of(frame.getResultFileName()).toFile());
                } catch (IOException ex) {
                    frame.showResult(new Result(ResultCode.ERROR, ex));
                }
                break;
            case AppWindow.OPEN_DIR:
                try {
                    Desktop.getDesktop().open(Path.of(frame.getResultFileName()).toAbsolutePath().getParent().toFile());
                } catch (IOException ex) {
                    frame.showResult(new Result(ResultCode.ERROR, ex));
                }
                break;
            case AppDialog.CHANGE:
                try {
                    changeSymbolPairs();
                } catch (IOException|UserException ex) {
                    frame.showResult(new Result(ResultCode.ERROR, ex));
                }
                break;
            case AppWindow.ABOUT:
                JOptionPane.showMessageDialog(frame, AppWindow.DEVELOPER_NAME, AppWindow.DEVELOPER, JOptionPane.INFORMATION_MESSAGE);
                break;
            case AppWindow.EXIT:
                System.exit(0);
                break;
        }
    }

    /**
     * Шифрование
     */
    private void encode() {
        try {
            String encodedText = Application.crypt(new String[]{
                Crypt.ENCRYPT,
                getKey(),
                frame.getEncryptFileName()
            });
            Path file = frame.saveTextToFile(encodedText, DefaultValues.ENCODED_FILE);
            frame.showResult(new Result(ResultCode.OK, file));
        } catch (Exception ex) {
            frame.showResult(new Result(ResultCode.ERROR, ex));
        }
    }

    /**
     * Расшифровка
     */
    private void decode() {
        try {
            String decodedText = Application.crypt(new String[]{
                Crypt.DECRYPT,
                frame.getKey(),
                frame.getDecryptFileName()
            });
            Path file = frame.saveTextToFile(decodedText, DefaultValues.DECODED_FILE);
            frame.showResult(new Result(ResultCode.OK, file));
        } catch (Exception ex) {
            frame.showResult(new Result(ResultCode.ERROR, ex));
        }
    }

    /**
     * Расшифровка перебором
     */
    private void bruteForce() {
        try {
            String decodedText = Application.crypt(new String[]{
                Crypt.BRUTE_FORCE,
                frame.getKey(),
                frame.getDecryptFileName()
            });
            Path file = frame.saveTextToFile(decodedText, DefaultValues.DECODED_FILE);
            frame.showResult(new Result(ResultCode.OK, file));
        } catch (Exception ex) {
            frame.showResult(new Result(ResultCode.ERROR, ex));
        }
    }

    /**
     * @return - возвращает путь к расшифрованному файлу методом анализа
     */
    private Path analyse() {
        try {
            String decodedText = Application.crypt(new String[]{
                Crypt.ANALYSIS,
                frame.getKey(),
                frame.getDecryptFileName(),
                frame.getDictionaryFileName()
            });
            Path file = frame.saveTextToFile(decodedText, DefaultValues.DECODED_FILE);
            frame.showResult(new Result(ResultCode.OK, file));
            return file;
        } catch (IOException ex) {
            frame.showResult(new Result(ResultCode.ERROR, ex));
        }
        return null;
    }

    /**
     * @return - возвращает ключ шифрования
     */
    private String getKey() {
        String key = frame.getKey();

        if (DefaultValues.EMPTY_STRING.equals(key)) {
            key = Caesar.generateKey();
            frame.setKey(key);
        }

        return key;
    }

    /**
     * Меняет в расшифровываемом тексте пары символов
     * @throws IOException
     */
    private void changeSymbolPairs() throws IOException {
        String text = frame.getDecodedTextArea().getText();
        String firstCharFrom = frame.getSymbolFrom().getText();
        String firstCharTo = frame.getSymbolTo().getText();

        if (firstCharFrom.length() < 1 || firstCharTo.length() < 1)
            throw new UserException(ExceptionConstant.ENTER_SYMBOL_PAIR);

        String resultText = Analysis.changeSymbolPairs(text, firstCharFrom.charAt(0), firstCharTo.charAt(0));
        frame.saveTextToFile(resultText, DefaultValues.DECODED_FILE);
        frame.getDecodedTextArea().setText(resultText);
        frame.setSymbolTo(DefaultValues.EMPTY_STRING);
        frame.setSymbolFrom(DefaultValues.EMPTY_STRING);
    }
}
