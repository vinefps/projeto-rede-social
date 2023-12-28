package com.example.demo1.Controller;

import com.example.demo1.Database.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {

    @FXML
    private TextField nome;

    @FXML
    private TextField email;

    @FXML
    private PasswordField senha;

    @FXML
    private PasswordField confirmaSenha;

    @FXML
    private DatePicker dtaNascimento;

    @FXML
    private TextField telefone;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void registrar() {
        Usuario usuario = new Usuario();

        String nomeText = nome.getText().trim();
        String emailText = email.getText().trim();
        String senhaText = senha.getText();
        String confirmaSenhaText = confirmaSenha.getText();

        LocalDate dtaNascimentoValue = dtaNascimento.getValue();
        String dtaNascimentoText = (dtaNascimentoValue != null) ? dtaNascimentoValue.toString() : "";

        String telefoneText = telefone.getText().trim();

        if (nomeText.isEmpty() || emailText.isEmpty() || senhaText.isEmpty() || confirmaSenhaText.isEmpty() || dtaNascimentoText.isEmpty() || telefoneText.isEmpty()) {
            ModalUtil.getInstance().showModal("Preencha todos os dados");
            return;
        }

        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailText);

        if (!matcher.matches()) {
            ModalUtil.getInstance().showModal("Informe um e-mail válido");
            return;
        }

        if (!senhaText.equals(confirmaSenhaText)) {
            ModalUtil.getInstance().showModal("As senhas devem ser iguais");
            return;
        }

        if (senhaText.length() < 8) {
            ModalUtil.getInstance().showModal("A senha deve conter \n no mínimo 8 caracteres");
            return;
        }

        String senhaHash = null;
        try {
            senhaHash = calcularSHA256(senhaText);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        LocalDate dataNascimento = LocalDate.parse(dtaNascimentoText);
        if (dataNascimento.isAfter(LocalDate.now())) {
            ModalUtil.getInstance().showModal("A data não pode ser maior que a data atual");
            return;
        }

        if (!telefoneText.matches("\\d+")) {
            ModalUtil.getInstance().showModal("Telefone deve conter \n    somente números");
            return;
        }

        if (telefoneText.length() < 8 || telefoneText.length() > 15) {
            ModalUtil.getInstance().showModal("Telefone Inválido");
            return;
        }

        String telefoneLimpo = telefoneText.replaceAll("[^0-9]", "");

        usuario.nome = nomeText;
        usuario.email = emailText;
        usuario.senha = senhaHash;
        usuario.dtaNascimento = dtaNascimentoText;
        usuario.telefone = telefoneLimpo;

        if (usuario.incluirUsuario()) {
            System.out.println("Usuário salvo no banco de dados com sucesso!");
            ModalUtil.getInstance().showModal("Usuário salvo no banco de dados \n com sucesso!");
        } else {
            System.err.println("Erro ao salvar usuário no banco de dados.");
            ModalUtil.getInstance().showModal("Erro ao salvar usuário \n no banco de dados.");
        }

        System.out.println("Registro bem-sucedido:");
        System.out.println("Nome: " + nomeText);
        System.out.println("Email: " + emailText);
        System.out.println("Senha: " + senhaHash);
        System.out.println("Data de Nascimento: " + dtaNascimentoText);
        System.out.println("Telefone: " + telefoneLimpo);

        if (this.stage != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/hello-view.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root, 300, 400);

                this.stage.setTitle("Login");
                this.stage.setScene(scene);
                this.stage.setResizable(false);
                this.stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Erro: A referência da Stage não foi inicializada.");
        }
    }

    public static String calcularSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = messageDigest.digest(input.getBytes());

        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) {
                hexStringBuilder.append('0');
            }
            hexStringBuilder.append(hex);
        }

        return hexStringBuilder.toString();
    }

    @FXML
    public void backLogin() throws IOException {
        if (this.stage != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/hello-view.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 300, 400);
            this.stage.setTitle("Login");
            this.stage.setScene(scene);
            this.stage.setResizable(false);
        } else {
            System.err.println("Erro: A referência da Stage não foi inicializada.");
        }
    }
}
