package com.javarush.cryptanalyzer.cooper.view;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Files;
import javax.swing.border.EmptyBorder;
import com.javarush.cryptanalyzer.cooper.constants.AppWindow;
import com.javarush.cryptanalyzer.cooper.constants.Exception;
import com.javarush.cryptanalyzer.cooper.exception.UserException;

public class Window extends JFrame {
    private final int width;
    private final int height;

    WindowListener windowListener;
    JPanel jPanelTop, jPanelMiddle, jPanelBottom;
    JLabel filePathLabel, dictionaryPathLabel;
    JTextField offsetTextField;
    JLabel messageLabel, resultFileLabel;
    JButton fileOpenButton, dirOpenButton;

    public Window() {
        super(AppWindow.APP_NAME);
        this.width = AppWindow.DEFAULT_WIDTH;
        this.height = AppWindow.DEFAULT_HEIGHT;
        windowListener = new WindowListener(this);
        drawWindow();
    }

    public Window(int width, int height) {
        super(AppWindow.APP_NAME);
        this.width = width;
        this.height = height;
        windowListener = new WindowListener(this);
        drawWindow();
    }

    /**
     * Рисует окно программы
     */
    private void drawWindow() {
        setMainFrame();
        setMainMenu();
        initPanels();
        fillPanelTop();
        fillPanelMiddle();
        fillPanelBottom();


        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        //gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(jPanelTop, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        add(jPanelMiddle, gridBagConstraints);
        gridBagConstraints.gridy = 2;
        add(jPanelBottom, gridBagConstraints);

//        add(jPanelTop, BorderLayout.NORTH);
//        add(jPanelMiddle, BorderLayout.CENTER);
//        add(jPanelBottom, BorderLayout.SOUTH);
    }

    /**
     * Задает параметры основного фрейма
     */
    private void setMainFrame() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((size.width / 2) - (width / 2), (size.height / 2) - (height / 2), width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
    }

    /**
     * Создает главное меню
     */
    private void setMainMenu() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu menuFile = new JMenu(AppWindow.FILE);
        JMenu menuHelp = new JMenu(AppWindow.HELP);

        JMenuItem itemEncrypt = new JMenuItem(AppWindow.ENCRYPT);
        JMenuItem itemDecrypt = new JMenuItem(AppWindow.DECRYPT);
        JMenuItem itemBrutForce = new JMenuItem(AppWindow.BRUTE_FORCE);
        JMenuItem itemAnalysis = new JMenuItem(AppWindow.ANALYSIS);
        JMenuItem itemExit = new JMenuItem(AppWindow.EXIT);
        JMenuItem itemAbout = new JMenuItem(AppWindow.ABOUT);

        menuFile.add(itemEncrypt);
        menuFile.add(itemDecrypt);
        menuFile.add(itemBrutForce);
        menuFile.add(itemAnalysis);
        menuFile.addSeparator();
        menuFile.add(itemExit);
        menuHelp.add(itemAbout);

        jMenuBar.add(menuFile);
        jMenuBar.add(menuHelp);
        setJMenuBar(jMenuBar);

        itemEncrypt.addActionListener(windowListener);
        itemDecrypt.addActionListener(windowListener);
        itemBrutForce.addActionListener(windowListener);
        itemAnalysis.addActionListener(windowListener);
        itemAbout.addActionListener(windowListener);
        itemExit.addActionListener(windowListener);
    }

    /**
     * Инициализация панелей фрейма
     */
    private void initPanels() {
        jPanelTop = new JPanel();
        jPanelTop.setLayout(new GridBagLayout());
        jPanelTop.setAlignmentX(LEFT_ALIGNMENT);

        jPanelMiddle = new JPanel();
        jPanelMiddle.setLayout(new GridBagLayout());
        jPanelMiddle.setBorder(new EmptyBorder(30, 20, 20, 20));

        jPanelBottom = new JPanel();
        jPanelBottom.setLayout(new GridBagLayout());
    }

    /**
     * Заполнение элементами верхней панели
     */
    private void fillPanelTop() {
        Dimension dimension = new Dimension(150, 25);

        JLabel userTextLabel = new JLabel(AppWindow.APP_MESSAGE);

        JButton fileButton = new JButton(AppWindow.FILE_ENCRYPT_DECRYPT);
        fileButton.setPreferredSize(dimension);
        filePathLabel = new JLabel(AppWindow.DEFAULT_ENCODED_FILE);

        JButton fileAnalysisButton = new JButton(AppWindow.FILE_DICTIONARY);
        fileAnalysisButton.setPreferredSize(dimension);
        dictionaryPathLabel = new JLabel(AppWindow.DEFAULT_DICTIONARY_FILE);

        offsetTextField = new JTextField();
        offsetTextField.setMinimumSize(dimension);
        JLabel offsetLabel = new JLabel(AppWindow.KEY);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 5, 0, 5);
        jPanelTop.add(userTextLabel, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanelTop.add(fileButton, gridBagConstraints);
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.gridx = 1;
        jPanelTop.add(filePathLabel, gridBagConstraints);
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanelTop.add(fileAnalysisButton, gridBagConstraints);
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.gridx = 1;
        jPanelTop.add(dictionaryPathLabel, gridBagConstraints);
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanelTop.add(offsetTextField, gridBagConstraints);
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.gridx = 1;
        jPanelTop.add(offsetLabel, gridBagConstraints);

        fileButton.addActionListener(windowListener);
        fileAnalysisButton.addActionListener(windowListener);
    }

    /**
     * Заполнение элементами средней панели
     */
    private void fillPanelMiddle() {
        Dimension dimension = new Dimension(200, 25);

        JButton encryptButton = new JButton(AppWindow.ENCRYPT);
        encryptButton.setPreferredSize(dimension);

        JButton decryptButton = new JButton(AppWindow.DECRYPT);
        decryptButton.setPreferredSize(dimension);

        JButton brutForceButton = new JButton(AppWindow.BRUTE_FORCE);
        brutForceButton.setPreferredSize(dimension);

        JButton analysisButton = new JButton(AppWindow.ANALYSIS);
        analysisButton.setPreferredSize(dimension);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, 5, 5, 5);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanelMiddle.add(encryptButton, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        jPanelMiddle.add(decryptButton, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanelMiddle.add(brutForceButton, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        jPanelMiddle.add(analysisButton, gridBagConstraints);

        encryptButton.addActionListener(windowListener);
        decryptButton.addActionListener(windowListener);
        brutForceButton.addActionListener(windowListener);
        analysisButton.addActionListener(windowListener);
    }

    /**
     * Заполнение элементами нижней панели
     */
    private void fillPanelBottom() {
        Dimension dimension = new Dimension(120, 25);

        messageLabel = new JLabel(" ");
        messageLabel.setPreferredSize(dimension);
        messageLabel.setForeground(Color.BLUE);

        resultFileLabel = new JLabel(" ");
        resultFileLabel.setPreferredSize(dimension);
        resultFileLabel.setForeground(Color.BLUE);

        fileOpenButton = new JButton(AppWindow.OPEN_FILE);
        fileOpenButton.setPreferredSize(dimension);
        fileOpenButton.setEnabled(false);
        //fileOpenButton.setVisible(false);

        dirOpenButton = new JButton(AppWindow.OPEN_DIR);
        dirOpenButton.setPreferredSize(dimension);
        dirOpenButton.setEnabled(false);
        //dirOpenButton.setVisible(false);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, 5, 10, 5);
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanelBottom.add(messageLabel, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        jPanelBottom.add(resultFileLabel, gridBagConstraints);
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridy = 2;
        jPanelBottom.add(fileOpenButton, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        jPanelBottom.add(dirOpenButton, gridBagConstraints);

        fileOpenButton.addActionListener(windowListener);
        dirOpenButton.addActionListener(windowListener);
    }

    /**
     * Записывает путь к выбранному файлу
     * @param path - путь к файлу
     */
    public void setFilePath(String path) {
        filePathLabel.setText(path);
    }

    /**
     * Записывает пусть к выбранному файлу для аналитики
     * @param path - путь к файлу
     */
    public void setAnalysisFilePath(String path) {
        dictionaryPathLabel.setText(path);
    }

    /**
     * @param fileType - тип файла (исходный файл/файл для аналитики)
     * @return - возвращает путь к выбранному файлу
     */
    public Path getFilePath(String fileType) {
        String file = null;
        if (fileType.equals(AppWindow.TYPE_FILE)) file = filePathLabel.getText();
        else if (fileType.equals(AppWindow.TYPE_DICTIONARY)) file = dictionaryPathLabel.getText();

        if (file == null) throw new UserException(Exception.FILE_NOT_SELECTED);

        Path filePath = Path.of(file);

        if (!Files.isRegularFile(filePath)) throw new UserException(Exception.WRONG_FILE_PATH);

        return filePath;
    }

    /**
     * @return - возвращает сдвиг для шифрования, введенный пользователем
     */
    public int getKey() {
        int key;
        try {
            key = Integer.parseInt(offsetTextField.getText());
            if (key < 0) throw new UserException(Exception.WRONG_KEY);
        } catch (NumberFormatException e) {
            throw new UserException(Exception.WRONG_KEY);
        }

        return key;
    }

    /**
     * @return - возвращает путь к целевому файлу после шифрования/расшифровки
     */
    public Path getResultFile() {
        return Path.of(resultFileLabel.getText());
    }

    /**
     * Активирует кнопки открытия итогового файла/директории и записывает путь к нему в поле
     * @param file - путь к файлу
     */
    public void showResult(Path file, int key, String message) {
        if (file != null && Files.isRegularFile(file) && Files.isDirectory(file.toAbsolutePath().getParent())) {
            messageLabel.setText(message + (key != 0 ? Math.abs(key) : ""));
            resultFileLabel.setText(file.toAbsolutePath().toString());
            fileOpenButton.setEnabled(true);
            dirOpenButton.setEnabled(true);
            //fileOpenButton.setVisible(true);
            //dirOpenButton.setVisible(true);
        }
    }

    /**
     * Деактивирует кнопки открытия итогового файла/директории и очищает поле с путем к нему
     */
    public void clearResult() {
        messageLabel.setText(" ");
        resultFileLabel.setText(" ");
        fileOpenButton.setEnabled(false);
        dirOpenButton.setEnabled(false);
        //fileOpenButton.setVisible(false);
        //dirOpenButton.setVisible(false);
    }
}
