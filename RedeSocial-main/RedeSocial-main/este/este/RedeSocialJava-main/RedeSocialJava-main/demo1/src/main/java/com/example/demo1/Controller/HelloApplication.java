package com.example.demo1.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static int qrId = 0;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        showLoginScene();
    }

    public void showLoginScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/hello-view.fxml"));
            Parent root = loader.load();

            // Obtém o controlador associado à cena
            HelloController helloController = loader.getController();

            // Define o stage no controlador
            helloController.setStage(primaryStage);

            Scene scene = new Scene(root, 300, 350);
            scene.getStylesheets().add(getClass().getResource("/com/example/demo1/styles.css").toExternalForm());

            primaryStage.setTitle("Login");
            primaryStage.setScene(scene);

            // Configura a janela para não ser redimensionável
            primaryStage.setResizable(false);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void getUserId(int userId) {
        qrId = userId;
    };

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
