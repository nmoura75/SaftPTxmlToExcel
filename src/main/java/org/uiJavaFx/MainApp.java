package org.uiJavaFx;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.xmlParseExcel.Constants;

import java.io.File;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Enter XML File Path:");
        TextField textField = new TextField();
        textField.setText(Constants.XML_FILE_PATH); // Set initial value

        Button updateButton = new Button("Update Path");
        updateButton.setOnAction(e -> {
            Constants.XML_FILE_PATH = textField.getText();
            System.out.println("Updated XML_FILE_PATH: " + Constants.XML_FILE_PATH);
        });

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));

        Button browseButton = new Button("Browse...");
        browseButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                textField.setText(selectedFile.getAbsolutePath());
            }
        });

        VBox root = new VBox(10, label, textField, browseButton, updateButton);

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("XML to Excel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
