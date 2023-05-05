package com.javarush.cryptanalyzer.cooper.view;

import java.awt.*;
import javax.swing.*;
import java.nio.file.Path;
import javax.swing.border.EmptyBorder;
import com.javarush.cryptanalyzer.cooper.constants.AppWindow;
import com.javarush.cryptanalyzer.cooper.constants.DefaultFiles;

public class GUIView extends JFrame implements View {
    private final int width;
    private final int height;

    ViewListener listener;
    JPanel jPanelTop, jPanelMiddle, jPanelBottom;
    JLabel filePathLabel, dictionaryPathLabel;
    JTextField offsetTextField;
    JLabel messageLabel, resultFileLabel;
    JButton fileOpenButton, dirOpenButton;

    public GUIView() {
        super(AppWindow.APP_NAME);
        this.width = AppWindow.DEFAULT_WIDTH;
        this.height = AppWindow.DEFAULT_HEIGHT;
        listener = new ViewListener(this);
    }

    public GUIView(int width, int height) {
        super(AppWindow.APP_NAME);
        this.width = width;
        this.height = height;
        listener = new ViewListener(this);
    }

    public ViewListener getListener() {
        return listener;
    }

    /**
     * Рисует окно программы
     */
    public void start() {
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

        setVisible(true);
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

        itemEncrypt.addActionListener(listener);
        itemDecrypt.addActionListener(listener);
        itemBrutForce.addActionListener(listener);
        itemAnalysis.addActionListener(listener);
        itemAbout.addActionListener(listener);
        itemExit.addActionListener(listener);
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
        filePathLabel = new JLabel(DefaultFiles.INPUT_FILE);

        JButton fileAnalysisButton = new JButton(AppWindow.FILE_DICTIONARY);
        fileAnalysisButton.setPreferredSize(dimension);
        dictionaryPathLabel = new JLabel(DefaultFiles.DICTIONARY_FILE);

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

        fileButton.addActionListener(listener);
        fileAnalysisButton.addActionListener(listener);
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

        encryptButton.addActionListener(listener);
        decryptButton.addActionListener(listener);
        brutForceButton.addActionListener(listener);
        analysisButton.addActionListener(listener);
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

        fileOpenButton.addActionListener(listener);
        dirOpenButton.addActionListener(listener);
    }

    /**
     * @return - возвращает сдвиг для шифрования, введенный пользователем
     */
    public String getKey() {
        return offsetTextField.getText();
    }

    public String getCryptFileName() {
        return filePathLabel.getText();
    }

    public String getDictionaryFileName() {
        return dictionaryPathLabel.getText();
    }

    /**
     * @return - возвращает путь к целевому файлу после шифрования/расшифровки
     */
    public String getResultFileName() {
        return resultFileLabel.getText();
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
     * Записывает пусть к выбранному файлу для аналитики
     * @param path - путь к файлу
     */
    public void setResultFilePath(String path) {
        resultFileLabel.setText(path);
    }

    public void showResult(Path file, int key, String message) {
    }

    @Override
    public void printResult() {
//        if (file != null && Files.isRegularFile(file) && Files.isDirectory(file.toAbsolutePath().getParent())) {
//            messageLabel.setText(message + (key != 0 ? Math.abs(key) : ""));
//            resultFileLabel.setText(file.toAbsolutePath().toString());
//            fileOpenButton.setEnabled(true);
//            dirOpenButton.setEnabled(true);
//            //fileOpenButton.setVisible(true);
//            //dirOpenButton.setVisible(true);
//        }
    }

    @Override
    public void clearResult() {
        messageLabel.setText(" ");
        resultFileLabel.setText(" ");
        fileOpenButton.setEnabled(false);
        dirOpenButton.setEnabled(false);
        //fileOpenButton.setVisible(false);
        //dirOpenButton.setVisible(false);
    }
}
