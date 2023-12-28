package com.example.demo1.Controller;

import com.example.demo1.Database.Conexao;
import com.example.demo1.Database.Mensagem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.util.Map;


public class ChatController {

    @FXML
    private ListView<String> chatListView;

    @FXML
    private TextField messageTextField;

    @FXML
    private Button sendButton;

    private int idUsuarioAmigo;

    private String nome_amigo;

    private int qrIdUsuarioAmigo;

    private int qrId; // Adicione este campo


    public void setQrId(int qrId) {
        this.qrId = qrId;
    }

    public void setQrIdUsuarioAmigo(int qrIdUsuarioAmigo) {
        this.qrIdUsuarioAmigo = qrIdUsuarioAmigo;
    }

    public void setIdUsuarioAmigo(int idUsuarioAmigo) {
        this.idUsuarioAmigo = idUsuarioAmigo;
    }

    public int getIdUsuarioAmigo() {
        return idUsuarioAmigo;
    }

    public void setNomeAmigo(String nome_amigo) {
        this.nome_amigo = nome_amigo;
    }

    public String getMessageTextFieldText() {
        return messageTextField.getText();
    }


    @FXML
    public void sendMessage(ActionEvent event) {
        String message = messageTextField.getText();
        if (!message.isEmpty()) {
            chatListView.getItems().add("Você: " + message);
            messageTextField.clear();
            Conexao.enviarMensagem(qrId, idUsuarioAmigo, message);
        } else {
            // Adicione lógica aqui para lidar com mensagens vazias, se necessário
            System.out.println("A mensagem está vazia. Não será enviada.");
        }
    }


        public void exibirMensagemSimplificado(Map<String, Object> mensagem) {
        int idUsuarioRemetente = (int) mensagem.get("idUsuario");
        String conteudo = (String) mensagem.get("conteudo");

        String quemEnviou = (idUsuarioRemetente == qrId) ? "Você" : "Amigo";
        String mensagemFormatada = quemEnviou + ": " + conteudo;
        chatListView.getItems().add(mensagemFormatada);
    }


}
