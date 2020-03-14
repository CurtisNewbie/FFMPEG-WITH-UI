package com.curtisnewbie.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.curtisnewbie.app.App;
import com.curtisnewbie.util.FfmpegConvert;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;

/**
 * ------------------------------------
 * 
 * Author: Yongjie Zhuang
 * 
 * ------------------------------------
 * <p>
 * Controller that manages interaction with UI
 * </p>
 */
public class Controller implements Initializable {

    @FXML
    private TextArea resultTextArea;

    @FXML
    private Button selectInDirBtn;

    @FXML
    private Button selectOutDirBtn;

    @FXML
    private Button convertBtn;

    @FXML
    private TextField inDirTextField;

    @FXML
    private TextField outDirTextField;

    @FXML
    private TextField formatTextField;

    private Loggable logger;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.logger = new Logger(this);
        registerSelectInDirEventHandler();
        registerSelectOutDirEventHandler();
        registerConvertEventHandler();
    }

    /**
     * Register EventHandler for selectInDirBtn
     */
    private void registerSelectInDirEventHandler() {
        this.selectInDirBtn.setOnAction(e -> {
            var dir = useDirChooser("Select Directory/ Folder");
            setInDir(dir.getAbsolutePath());
        });
    }

    /**
     * Register EventHandler for selectOutDirBtn
     */
    private void registerSelectOutDirEventHandler() {
        this.selectOutDirBtn.setOnAction(e -> {
            var dir = useDirChooser("Select Directory/ Folder");
            setOutDir(dir.getAbsolutePath());
        });
    }

    /**
     * Register EventHandler for convertBtn
     */
    private void registerConvertEventHandler() {
        this.convertBtn.setOnAction(e -> {
            var inDir = getInDir();
            var outDir = getOutDir();
            var format = getFormat();
            if (notNullnHasChar(inDir) && notNullnHasChar(outDir) && notNullnHasChar(format)) {
                new Thread(() -> {
                    FfmpegConvert.convert(inDir, outDir, format, this.logger);
                }).start();
            }
        });
    }

    public void setInDir(String inPath) {
        this.inDirTextField.setText(inPath);
    }

    public String getInDir() {
        return this.inDirTextField.getText();
    }

    public String getOutDir() {
        return this.outDirTextField.getText();
    }

    public void setOutDir(String outPath) {
        this.outDirTextField.setText(outPath);
    }

    public void setFormat(String format) {
        this.formatTextField.setText(format);
    }

    public String getFormat() {
        return this.formatTextField.getText();
    }

    public String getResultText() {
        return this.resultTextArea.getText();
    }

    public void setResultText(String text) {
        this.resultTextArea.setText(text);
    }

    /**
     * Create a DirectoryChooser for selecting a directory
     * 
     * @param title title of the created DirectoryChooser
     * @return selected directory
     */
    public File useDirChooser(String title) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle(title);
        File selectedDir = dirChooser.showDialog(App.getPrimaryStage());
        return selectedDir;
    }

    private boolean notNullnHasChar(String str) {
        return str != null && str.length() > 0;
    }

    /**
     * Create an Alert dialog to show error msg
     * 
     * @param msg
     */
    public void showError(String msg) {
        Platform.runLater(() -> {
            var alert = new Alert(AlertType.ERROR);
            alert.setContentText(msg);
            alert.show();
        });
    }

    /**
     * Create an Alert dialog to show info msg
     * 
     * @param msg
     */
    private void showInfo(String msg) {
        Platform.runLater(() -> {
            var alert = new Alert(AlertType.INFORMATION);
            alert.setContentText(msg);
            alert.show();
        });
    }

    /**
     * Append msg to resultTextArea
     * 
     * @param msg
     */
    public void appendResultTextArea(String msg) {
        this.resultTextArea.appendText(msg);
    }

    /**
     * For logging any info or error occured during convertion.
     */
    class Logger implements Loggable {

        private Controller controller;

        public Logger(Controller controller) {
            this.controller = controller;
        }

        @Override
        public void appendResult(String msg) {
            controller.appendResultTextArea(msg + "\n");
        }

        @Override
        public void error(String msg) {
            controller.showError(msg);
        }

        @Override
        public void info(String msg) {
            controller.showInfo(msg);
        }
    }

}