package com.javarush.cryptanalyzer.cooper.view;

import java.awt.*;
import javax.swing.*;
import java.nio.file.Path;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.javarush.cryptanalyzer.cooper.entity.Result;
import com.javarush.cryptanalyzer.cooper.app.Application;
import com.javarush.cryptanalyzer.cooper.constants.Crypt;
import com.javarush.cryptanalyzer.cooper.exception.UserException;
import com.javarush.cryptanalyzer.cooper.utils.ResultCode;
import com.javarush.cryptanalyzer.cooper.constants.AppWindow;
import com.javarush.cryptanalyzer.cooper.constants.DefaultFiles;

public class ViewListener implements ActionListener {
    GUIView frame;

    public ViewListener(GUIView frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case AppWindow.FILE_ENCRYPT_DECRYPT:
                frame.clearResult();
                frame.setCryptFileName(frame.selectFile());
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
                analyse();
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
            case "About":
                JOptionPane.showMessageDialog(frame, AppWindow.DEVELOPER_NAME, AppWindow.DEVELOPER, JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Exit":
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
                frame.getKey(),
                frame.getCryptFileName()
            });
            Path file = frame.saveTextToFile(encodedText, DefaultFiles.ENCODED_FILE);
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
                frame.getCryptFileName()
            });
            Path file = frame.saveTextToFile(decodedText, DefaultFiles.DECODED_FILE);
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
                frame.getCryptFileName()
            });
            Path file = frame.saveTextToFile(decodedText, DefaultFiles.DECODED_FILE);
            frame.showResult(new Result(ResultCode.OK, file));
        } catch (Exception ex) {
            frame.showResult(new Result(ResultCode.ERROR, ex));
        }
    }

    /**
     * Расшифровка анализом
     */
    private void analyse() {
        try {
            String decodedText = Application.crypt(new String[]{
                Crypt.ANALYSIS,
                frame.getKey(),
                frame.getCryptFileName(),
                frame.getDictionaryFileName()
            });
            Path file = frame.saveTextToFile(decodedText, DefaultFiles.DECODED_FILE);
            frame.showResult(new Result(ResultCode.OK, file));
        } catch (IOException ex) {
            frame.showResult(new Result(ResultCode.ERROR, ex));
        }
    }
}
