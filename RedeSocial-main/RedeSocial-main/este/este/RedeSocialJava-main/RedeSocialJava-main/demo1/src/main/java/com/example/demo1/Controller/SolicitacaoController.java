package com.example.demo1.Controller;
import com.example.demo1.Database.Conexao;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox; // Importante adicionar esta linha

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolicitacaoController extends Conexao {
    public static int qrId = 0;
    public static String userNome = "";
    @FXML
    private ListView<String> listaSolicitacoes;

    @FXML
    private VBox vboxDynamicLabels; // Corrigido para VBox

    public void initialize() {
        // Obter a lista de amigos usando o método da classe Conexao
        Map<String, List<Object>> consultarListaSolicitacoes = Conexao.consultarListaSolicitacoes(qrId);

        if (consultarListaSolicitacoes != null && !consultarListaSolicitacoes.isEmpty()) {
            List<Object> listaIdsSolicitacaoObj = consultarListaSolicitacoes.get("listaIdsSolicitacao");
            List<Object> listaStringNomesObj = consultarListaSolicitacoes.get("listaStringNomes");
            List<Object> listaIdAmigoObj = consultarListaSolicitacoes.get("listaIdAmigo");

            for (int i = 0; i < listaIdsSolicitacaoObj.size(); i++) {
                if (listaIdsSolicitacaoObj.get(i) instanceof Integer &&
                        listaStringNomesObj.get(i) instanceof String &&
                        listaIdAmigoObj.get(i) instanceof Integer) {

                    int idSolicitacao = (int) listaIdsSolicitacaoObj.get(i);
                    String nome = (String) listaStringNomesObj.get(i);
                    int idAmigo = (int) listaIdAmigoObj.get(i);

                    Map<String, Object> dadosSolicitacao = new HashMap<>();
                    dadosSolicitacao.put("idSolicitacao", idSolicitacao);
                    dadosSolicitacao.put("nome", nome);
                    dadosSolicitacao.put("idAmigo", idAmigo);

                    HBox friendBox = new HBox(10);
                    Label label = new Label(nome);

                    Region region = new Region();
                    HBox.setHgrow(region, Priority.ALWAYS);

                    Button aceitarButton = new Button("Aceitar");
                    aceitarButton.setUserData(dadosSolicitacao);

                    aceitarButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5px 15px; -fx-border-radius: 5px; -fx-cursor: hand; -fx-margin-right: 10px;");
                    aceitarButton.setOnAction(event -> {
                        Map<String, Object> valorUserData = (Map<String, Object>) aceitarButton.getUserData();
                        System.out.println((String) valorUserData.get("nome"));

                        aceitarListaSolicitacao(qrId, (int) valorUserData.get("idSolicitacao"), (String) valorUserData.get("nome"), (int) valorUserData.get("idAmigo"),userNome );
                        removerListaSolicitacaodois(qrId, idSolicitacao);

                    });

                    Button recusarButton = new Button("Recusar");
                    recusarButton.setStyle("-fx-background-color: #8B0000; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5px 15px; -fx-border-radius: 5px; -fx-cursor: hand; -fx-margin-right: 10px;");
                    recusarButton.setOnAction(event -> {
                        removerListaSolicitacao(qrId, idSolicitacao);
                    });

                    friendBox.getChildren().addAll(label, region, aceitarButton, recusarButton);
                    vboxDynamicLabels.getChildren().add(friendBox);
                }
            }
        }
    }

    @FXML
    public static void getIdUsuario(int userId){
        qrId = userId;
    }
    public static void getNomeUsuario(String userName){
        userNome = userName;
    }

}
