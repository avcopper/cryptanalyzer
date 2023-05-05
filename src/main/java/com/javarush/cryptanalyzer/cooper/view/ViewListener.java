package com.javarush.cryptanalyzer.cooper.view;

import java.io.File;
import javax.swing.*;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileFilter;
import com.javarush.cryptanalyzer.cooper.app.Application;
import com.javarush.cryptanalyzer.cooper.constants.Crypt;
import com.javarush.cryptanalyzer.cooper.constants.AppWindow;

public class ViewListener implements ActionListener {
    GUIView frame;

    public ViewListener(GUIView frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case AppWindow.FILE_ENCRYPT_DECRYPT:
                frame.setFilePath(selectFile());
                break;
            case AppWindow.FILE_DICTIONARY:
                frame.setAnalysisFilePath(selectFile());
                break;
            case AppWindow.ENCRYPT:
                frame.clearResult();
                try {
                    Application.crypt(new String[]{
                        Crypt.ENCRYPT,
                        frame.getKey(),
                        frame.getCryptFileName()
                    });
                } catch (Exception ex) {
                    showError(ex.getMessage());
                }
                break;
            case AppWindow.DECRYPT:
                frame.clearResult();
                try {
                    Application.crypt(new String[]{
                            Crypt.DECRYPT,
                            frame.getKey(),
                            frame.getCryptFileName()
                    });
                } catch (Exception ex) {
                    showError(ex.getMessage());
                }
                break;
            case AppWindow.BRUTE_FORCE:
                try {
                    Application.crypt(new String[]{
                        Crypt.BRUTE_FORCE,
                        frame.getKey(),
                        frame.getCryptFileName()
                    });
                    //makePain();
                } catch (Exception ex) {
                    showError(ex.getMessage());
                }
                break;
            case AppWindow.ANALYSIS:
                try {
                    Application.crypt(new String[]{
                        Crypt.ANALYSIS,
                        frame.getKey(),
                        frame.getCryptFileName(),
                        frame.getDictionaryFileName()
                    });
                    //tryToDoSomethingStupid();
                } catch (IOException ex) {
                    showError(ex.getMessage());
                }
                break;
            case AppWindow.OPEN_FILE:
//                try {
//                    Desktop.getDesktop().open(frame.getResultFile().toFile());
//                } catch (IOException ex) {
//                    showError(ex.getMessage());
//                }
                break;
            case AppWindow.OPEN_DIR:
//                try {
//                    Desktop.getDesktop().open(frame.getResultFile().toAbsolutePath().getParent().toFile());
//                } catch (IOException ex) {
//                    showError(ex.getMessage());
//                }
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
     * @return - возвращает путь к выбранному файлу на диске
     */
    private String selectFile() {
        //frame.clearResult();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выбор файла");
        fileChooser.setCurrentDirectory(new File("./"));
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
            return fileChooser.getSelectedFile().getPath();
        }

        return null;
    }

    /**
     * Показ ошибки
     * @param message - сообщение для показа
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
