package org.uiJavaFx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.xmlParseExcel.Constants;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Enter XML File Path:");
        TextField textField = new TextField();
        textField.setText(Constants.XML_FILE_PATH); // Set initial value

        Button button = new Button("Update Path");
        button.setOnAction(e -> {
            Constants.XML_FILE_PATH = textField.getText();
            System.out.println("Updated XML_FILE_PATH: " + Constants.XML_FILE_PATH);
        });

        VBox root = new VBox(10, label, textField, button);



        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("XML to Excel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
