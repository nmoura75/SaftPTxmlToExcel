package org.uiJavaFx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xmlParseExcel.Constants;
import org.xmlParseExcel.SafTParserSalesInvoice;

import java.io.File;

public class MainApp extends Application {

    private static final Logger logger = LogManager.getLogger(SafTParserSalesInvoice.class);
    private ProgressBar progressBar;
    private Label progressLabel;

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

        Label fileCreated = new Label();
        Button createExcelButton = new Button("Create Excel File");
        createExcelButton.setOnAction(e -> {
            // Reset Progress Bar
            progressBar.setProgress(0.0);
            progressLabel.setText("0%");

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    SafTParserSalesInvoice.main(new String[]{}, new SafTParserSalesInvoice.ProgressListener() {

                        public void onProgressUpdate(double progress) {
                            Platform.runLater(() -> {
                                progressBar.setProgress(progress);
                                progressLabel.setText(String.format("%.0f%%", progress * 100));
                            });
                        }
                    });
                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("XML data successfully converted to Excel file: " + Constants.EXCEL_FILE_PATH);
                        System.out.println("XML data successfully converted to Excel file" + Constants.EXCEL_FILE_PATH);
                        alert.showAndWait();
                        // Reset progress bar after success
                        progressBar.setProgress(0.0);
                        progressLabel.setText("0%");
                    });
                }

                @Override
                protected void failed() {
                    super.failed();
                    Throwable ex = getException();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error during processing");
                        alert.setContentText("An error occurred: " + ex.getMessage());
                        alert.showAndWait();
                        // Reset progress bar after error
                        progressBar.setProgress(0.0);
                        progressLabel.setText("0%");
                    });
                    logger.error("Error processing XML or creating excel file", ex);
                }
            };
            new Thread(task).start();
        });

        // Reset Progress Bar
        progressBar = new ProgressBar(0.0);
        progressLabel = new Label("0%");
        HBox progressBox = new HBox(10, progressBar, progressLabel);
        progressBox.setAlignment(Pos.CENTER);
        progressBar.setPrefWidth(200);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> primaryStage.close());

        VBox root = new VBox(20, selectXmlLabel, textFieldForSelectXml, browseButton,
                saveLabel, textFieldForSave, saveButton, createExcelButton, fileCreated, progressBox, closeButton);

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("XML to Excel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
