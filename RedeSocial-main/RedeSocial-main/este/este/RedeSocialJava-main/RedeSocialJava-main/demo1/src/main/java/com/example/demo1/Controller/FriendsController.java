package com.example.demo1.Controller;

import com.example.demo1.Database.Conexao;
import com.example.demo1.Controller.ChatController;


import com.example.demo1.Database.Mensagem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class FriendsController extends Conexao {

    public static int qrId = 0;

    @FXML
    private VBox vboxDynamicLabels; // Referência ao VBox dinâmico do FXML
    private BorderPane mainBorderPane; // Referência ao BorderPane principal

    // Método para definir o BorderPane principal
    public void setMainBorderPane(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @FXML
    public void initialize() {
        // Obter a lista de amigos usando o método da classe Conexao
        List<Map<String, Object>> consultarListaAmigos = Conexao.consultarListaAmigos(qrId);

        // Verificar se a lista não está vazia antes de iterar sobre ela
        if (consultarListaAmigos != null && !consultarListaAmigos.isEmpty()) {
            // Adiciona um Label, um botão de chat e um botão de remover para cada amigo na lista
            for (Map<String, Object> amigo : consultarListaAmigos) {
                HBox friendBox = new HBox(10);

                // Extrai o nome e o idUsuarioAmigo do Map
                String nomeAmigo = (String) amigo.get("nome_amigo");
                int idUsuarioAmigo = (int) amigo.get("idUsuarioAmigo");

                Label label = new Label(nomeAmigo);

                Region region = new Region();
                HBox.setHgrow(region, Priority.ALWAYS);

                Button chatButton = new Button("Chat");
                chatButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5px 15px; -fx-border-radius: 5px; -fx-cursor: hand; -fx-margin-right: 10px;");
                chatButton.setOnAction(event -> {
                    try {
                        System.out.println(idUsuarioAmigo);
                        entrarChat(idUsuarioAmigo);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                Button removerButton = new Button("Remover");
                removerButton.setStyle("-fx-background-color: #8B0000; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5px 15px; -fx-border-radius: 5px; -fx-cursor: hand; -fx-margin-right: 10px;");
                removerButton.setOnAction(event -> removerListaAmigos(qrId, idUsuarioAmigo));

                friendBox.getChildren().addAll(label, region, chatButton, removerButton);
                vboxDynamicLabels.getChildren().add(friendBox);
            }
        }
    }

    // Métodos de ação para os botões


    @FXML
    private void entrarChat(int idUsuarioAmigo) throws IOException {
        // Consulta o banco de dados para obter informações do amigo
        List<Map<String, Object>> amigos = Conexao.consultarListaAmigos(qrId);

        // Procura o amigo com o ID correspondente
        Map<String, Object> amigoEncontrado = amigos.stream()
                .filter(amigo -> (int) amigo.get("idUsuarioAmigo") == idUsuarioAmigo)
                .findFirst()
                .orElse(null);

        if (amigoEncontrado != null) {
            String nomeAmigo = (String) amigoEncontrado.get("nome_amigo");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/ChatView.fxml"));
            BorderPane chatScreen = fxmlLoader.load();

            // Obtém o controlador do ChatController e configura o ID e o nome do usuário amigo
            ChatController chatController = fxmlLoader.getController();
            chatController.setIdUsuarioAmigo(idUsuarioAmigo);
            chatController.setQrId(qrId); // Adicione esta linha

            // Obtém o texto da mensagem do TextField
            String mensagemEnviada = chatController.getMessageTextFieldText();

            if (mensagemEnviada != null && !mensagemEnviada.isEmpty()) {
                Conexao.enviarMensagem(qrId, idUsuarioAmigo, mensagemEnviada);
            } else {
                System.out.println("A mensagem está vazia ou nula. Não será enviada.");
            }

            // Obtém e exibe as mensagens existentes
            List<Map<String, Object>> mensagensSimplificadas = Conexao.obterMensagensSimplificado(qrId, idUsuarioAmigo);
            for (Map<String, Object> mensagem : mensagensSimplificadas) {
                int idUsuarioRemetente = (int) mensagem.get("idUsuario");
                String conteudo = (String) mensagem.get("conteudo");


                chatController.exibirMensagemSimplificado(mensagem);
                // Faça o que precisar com idUsuarioRemetente e conteudo
                System.out.println("ID do Remetente: " + idUsuarioRemetente);
                System.out.println("Conteúdo da Mensagem: " + conteudo);
            }


            // Obtém a cena da raiz do BorderPane
            mainBorderPane.setCenter(chatScreen);

            System.out.println("Entrar no chat");
        } else {
            System.out.println("Amigo não encontrado com o ID: " + idUsuarioAmigo);
        }
    }


    public static void getUsuarioid(int userId) {
        qrId = userId;
    }

    ;


}
