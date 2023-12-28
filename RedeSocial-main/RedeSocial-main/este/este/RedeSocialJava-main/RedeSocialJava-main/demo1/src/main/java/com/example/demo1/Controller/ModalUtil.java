package com.example.demo1.Controller;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModalUtil {

    private static ModalUtil instance;

    private ModalUtil() {
        // Construtor privado para garantir que a classe seja singleton
    }

    public static ModalUtil getInstance() {
        if (instance == null) {
            instance = new ModalUtil();
        }
        return instance;
    }

    public void showModal(String message) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.UTILITY);

        StackPane modalRoot = new StackPane();
        modalRoot.getChildren().add(new Label(message));

        Scene modalScene = new Scene(modalRoot, 200, 100);

        modalStage.setScene(modalScene);
        modalStage.setTitle("");
        modalStage.showAndWait();
    }
}

