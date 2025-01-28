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
        Label selectXmlLabel = new Label("Enter XML File Path:");
        TextField textFieldForSelectXml = new TextField();
        textFieldForSelectXml.setText(Constants.XML_FILE_PATH); // Set initial value

        Button browseButton = new Button("Browse...");
        browseButton.setOnAction(e -> {
            FileChooser fileChooserSelectXML = new FileChooser();
            fileChooserSelectXML.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            File selectedFile = fileChooserSelectXML.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                textFieldForSelectXml.setText(selectedFile.getAbsolutePath());
                Constants.XML_FILE_PATH = selectedFile.getAbsolutePath();
                System.out.println("Updated XML_FILE_PATH: " + Constants.XML_FILE_PATH);
            }
        });


        Label saveLabel = new Label("Select file path to save Excel:");
        TextField textFieldForSave = new TextField();
        textFieldForSave.setText(Constants.EXCEL_FILE_PATH); // Set initial value

        Button saveButton = new Button("Save file...");
        saveButton.setOnAction(e -> {
            FileChooser fileChooserSave = new FileChooser();
            fileChooserSave.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            File selectedFile = fileChooserSave.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                textFieldForSave.setText(selectedFile.getAbsolutePath());
                Constants.EXCEL_FILE_PATH = selectedFile.getAbsolutePath();
                System.out.println("Updated EXCEL_FILE_PATH: " + Constants.EXCEL_FILE_PATH);
            }
        });

        VBox root = new VBox(20, selectXmlLabel, textFieldForSelectXml, browseButton, saveLabel, textFieldForSave, saveButton);

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("XML to Excel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
