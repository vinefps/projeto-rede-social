package com.example.demo1.Controller;

import com.example.demo1.Database.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.io.IOException;

public class HelloController {

    private Stage primaryStage;

    @FXML
    private TextField Email;

    @FXML
    private PasswordField Password;

    @FXML
    private Button loginButton;

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    public void login() throws IOException {
        Usuario usuario = new Usuario();

        String emailText = this.Email.getText();
        String senhaText = this.Password.getText();

        if (!emailText.isEmpty() && !senhaText.isEmpty()) {
            int userId = usuario.realizarLogin(emailText, senhaText).getId();
            String nomeUser = usuario.realizarLogin(emailText, senhaText).getNome();

            UsuariosController.getUserId(userId);
            SolicitacaoController.getIdUsuario(userId);
            SolicitacaoController.getNomeUsuario(nomeUser);
            FriendsController.getUsuarioid((userId));

            if (userId != -1) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/dashboard-view.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root, 900, 600);
                    scene.getStylesheets().add(getClass().getResource("/com/example/demo1/styles.css").toExternalForm());

                    Stage dashboardStage = new Stage();
                    dashboardStage.setTitle("Dashboard");
                    dashboardStage.setScene(scene);
                    dashboardStage.setMinWidth(400);
                    dashboardStage.setMinHeight(300);
                    dashboardStage.setResizable(false);
                    dashboardStage.show();

                    Stage loginStage = (Stage) Email.getScene().getWindow();
                    loginStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            ModalUtil.getInstance().showModal("Login ou Senha incorretos");
            System.out.println("Login falhou!");
        }
    }

    @FXML
    public void goToCadastro() throws IOException {
        if (this.primaryStage != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/cadastro-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 300, 400);

            this.primaryStage.setTitle("Cadastro");
            this.primaryStage.setScene(scene);
            this.primaryStage.setResizable(false);
            this.primaryStage.show();

            RegisterController registerController = fxmlLoader.getController();
            registerController.setStage(this.primaryStage);
        } else {
            System.err.println("Erro: A referência da Stage não foi inicializada.");
        }
    }
}
