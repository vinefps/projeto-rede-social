package com.example.demo1.Controller;
import com.example.demo1.Database.Conexao;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class UsuariosController extends Conexao{
    public String nomeDoUsuario;
    public static int qrId = 0;
    @FXML
    private VBox amigosDimanico; // Referência ao VBox dinâmico do FXML

    private int userId; // Variável de instância para armazenar o userId


    // Método para definir o BorderPane principal
    public void setMainBorderPane(BorderPane mainBorderPane) {
        // Referência ao BorderPane principal
    }
    public static void getUserId(int userId) {
        qrId = userId;
    };




    @FXML
    public void initialize() {
        // Obter a lista de amigos usando o método da classe Conexao
        List<Map<String, Object>> listausuario = Conexao.listaUsuarios();

        // Verificar se a lista não está vazia antes de iterar sobre ela
        if (listausuario != null && !listausuario.isEmpty()) {

            // Verificar se qrId está presente na lista e salvar o nome do usuário correspondente
            Iterator<Map<String, Object>> iterator = listausuario.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> usuario = iterator.next();
                // Convertendo o valor associado à chave "id" para Integer
                Integer usuarioId = Integer.valueOf(usuario.get("id").toString());
                if (usuarioId.equals(qrId)) {
                    // Salvar o nome do usuário encontrado
                    nomeDoUsuario = usuario.get("nome").toString();
                    Conexao.getNomeUser(nomeDoUsuario);
                    iterator.remove();
                    break; // Se o ID foi encontrado, podemos sair do loop
                }
            }


            // Adiciona um Label para cada nome na lista
            for (Map<String, Object> usuario : listausuario) {

                HBox friendBox = new HBox(10);

                // Obter o valor associado à chave "nome"
                String nomeUsuario = usuario.get("nome").toString();
                String idUser = usuario.get("id").toString();


                Label label = new Label(nomeUsuario);

                Region region = new Region();
                HBox.setHgrow(region, Priority.ALWAYS);

                Button chatButton = new Button("Adicionar");
                chatButton.setUserData(usuario);  // Armazena informações do usuário no botão
                chatButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5px 15px; -fx-border-radius: 5px; -fx-cursor: hand; -fx-margin-right: 10px;");
                chatButton.setOnAction(event -> {
                    try {
                        enviarSolicitacao(chatButton);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });




                friendBox.getChildren().addAll(label, region, chatButton);
                amigosDimanico.getChildren().add(friendBox);
            }

        }
    }


    @FXML
    private void enviarSolicitacao(Button chatButton) throws IOException {
        Map<String, Object> usuario = (Map<String, Object>) chatButton.getUserData();

        // Obtém informações do usuário
        int idUsuario = qrId;
        int idAmigo = Integer.valueOf(usuario.get("id").toString());
        String nomeAmigo = usuario.get("nome").toString();
        Boolean status = true;


        Connection conexao = null;
        PreparedStatement statement = null;

        // Inserir dados na tabela de solicitação
       try {
            conexao = obterConexao();
            String sql = "INSERT INTO solicitacao (IdSolicitacao,idUsuario,idAmigo,nome_remetente,status,nome_destinatario) VALUES (DEFAULT, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = null;

            preparedStatement = conexao.prepareStatement(sql);

            preparedStatement.setInt(1, idAmigo);
            preparedStatement.setInt(2, idUsuario);
            preparedStatement.setString(3, nomeDoUsuario);
           preparedStatement.setBoolean(4, status);
           preparedStatement.setString(5, nomeAmigo);


            int linhasAfetadas = preparedStatement.executeUpdate();

            if (linhasAfetadas > 0) {

                ModalUtil.getInstance().showModal("Solicitação enviada!!");
                System.out.println("Solicitação enviada!!");
            } else {
                ModalUtil.getInstance().showModal("Erro ao enviar solicitação.");
                System.out.println("Erro ao enviar solicitação.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }




}
