package com.example.demo1.Controller;
import com.example.demo1.Database.Conexao;
import com.example.demo1.Database.Usuario;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DashboardController {
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private void showFriends() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/friends-view.fxml"));
        VBox friendsView = fxmlLoader.load();

        mainBorderPane.setCenter(friendsView);

        FriendsController friendsController = fxmlLoader.getController();

        friendsController.setMainBorderPane(mainBorderPane);
    }

    @FXML
    private void showRequests() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/solicitacao.fxml"));
        mainBorderPane.setCenter(fxmlLoader.load());
    }

    @FXML
    private void exitApplication() throws IOException {

        Stage stage = (Stage) mainBorderPane.getScene().getWindow();

        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/hello-view.fxml"));
        BorderPane loginView = loginLoader.load();

        Scene loginScene = new Scene(loginView, 300, 350);

        stage.setScene(loginScene);

        stage.centerOnScreen();

        stage.setResizable(false);

        stage.sizeToScene();
    }

    @FXML
    private void showUsuarios() throws IOException {
        System.out.println("Mostrar Usuários");

        List<Map<String, Object>> listausuario = Conexao.listaUsuarios();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/usuarios-view.fxml"));
        mainBorderPane.setCenter(fxmlLoader.load());
    }


}
