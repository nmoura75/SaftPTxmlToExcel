package org.uiJavaFx;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xmlParseExcel.Constants;
import org.xmlParseExcel.SafTParserSalesInvoice;

import java.io.File;

public class MainApp extends Application {

    private static final Logger logger = LogManager.getLogger(SafTParserSalesInvoice.class);
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
            } else {
                logger.error("Need to choose a xml file");
            }
        });


        Label saveLabel = new Label("Select path to save Excel:");
        TextField textFieldForSave = new TextField();
        textFieldForSave.setText(Constants.EXCEL_FILE_PATH); // Set initial value

        Button saveButton = new Button("Select folder...");
        saveButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            if (selectedDirectory != null) {
                String xmlFileName = new File(Constants.XML_FILE_PATH).getName();
                String excelFileName = xmlFileName.substring(0, xmlFileName.lastIndexOf('.')) + ".xls";
                String filePath = selectedDirectory.getAbsolutePath() + File.separator + excelFileName;
                textFieldForSave.setText(filePath);
                Constants.EXCEL_FILE_PATH = filePath;
                System.out.println("Updated EXCEL_FILE_PATH: " + Constants.EXCEL_FILE_PATH);
            } else {
                logger.error("Need to choose a folder to save excel file");
            }
        });

        Button createExcelButton = new Button("Create Excel File");
        createExcelButton.setOnAction(e -> {
            SafTParserSalesInvoice.main(new String[]{});
        });

        VBox root = new VBox(20, selectXmlLabel, textFieldForSelectXml, browseButton, saveLabel, textFieldForSave, saveButton, createExcelButton);

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("XML to Excel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
