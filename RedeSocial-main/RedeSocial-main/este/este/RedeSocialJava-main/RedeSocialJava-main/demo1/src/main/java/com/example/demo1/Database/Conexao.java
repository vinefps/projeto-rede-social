package com.example.demo1.Database;

import com.example.demo1.Controller.ModalUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conexao {

    public static int qrId = 0;
    private static final String URL = "jdbc:mysql://localhost:3306/java";
    private static final String USUARIO = "root";
    private static final String SENHA = "123456";

    public static Connection obterConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver do banco de dados não localizado", ex);
        }
    }

    public static void fecharRecursos(Connection conexao, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar recursos: " + ex.getMessage());
        }
    }


    // Exemplo de método para executar uma consulta no banco de dados
    public static void consultarUsuarios() {
        Connection conexao = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexao = obterConexao();
            statement = conexao.prepareStatement("SELECT * FROM USUARIO");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Telefone: " + resultSet.getString("telefone"));
            }
        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar o banco: " + ex.getMessage());
        } finally {
            fecharRecursos(conexao, statement, resultSet);
        }
    }

    // Método para consultar a lista de amigos
    public static List<Map<String, Object>> consultarListaAmigos(int idUsuario) {
        Connection conexao = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexao = obterConexao();
            String sql = "SELECT idUsuarioAmigo, nome_amigo FROM amigos WHERE idUsuario = ?";
            statement = conexao.prepareStatement(sql);
            statement.setInt(1, idUsuario); // Substitui o ponto de interrogação pelo idUsuario
            resultSet = statement.executeQuery();

            List<Map<String, Object>> listaAmigos = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> amigo = new HashMap<>();
                amigo.put("idUsuarioAmigo", resultSet.getInt("idUsuarioAmigo"));
                amigo.put("nome_amigo", resultSet.getString("nome_amigo"));
                listaAmigos.add(amigo);
            }

            return listaAmigos;
        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao consultar a lista de amigos: " + ex.getMessage());
            // Lidar com o erro, lançar uma exceção ou retornar null conforme necessário
            return null;
        } finally {
            fecharRecursos(conexao, statement, resultSet);
        }
    }


    public static Map<String, List<Object>> consultarListaSolicitacoes(int idUsuario) {
        Connection conexao = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexao = obterConexao();
            String sql = "SELECT nome_remetente, Idsolicitacao,IdAmigo FROM solicitacao WHERE idUsuario = ?";
            statement = conexao.prepareStatement(sql);
            statement.setInt(1, idUsuario);
            resultSet = statement.executeQuery();

            List<Integer> listaIdsSolicitacao = new ArrayList<>();
            List<String> listaStringNomes = new ArrayList<>();
            List<Integer> listaIdAmigo = new ArrayList<>();

            while (resultSet.next()) {
                int idAmigo = resultSet.getInt("IdAmigo");
                listaIdAmigo.add(idAmigo);
                int idSolicitacao = resultSet.getInt("Idsolicitacao");
                listaIdsSolicitacao.add(idSolicitacao);
                String nome = resultSet.getString("nome_remetente");
                listaStringNomes.add(nome);
            }

            Map<String, List<Object>> resultado = new HashMap<>();
            resultado.put("listaIdsSolicitacao", new ArrayList<>(listaIdsSolicitacao));
            resultado.put("listaStringNomes", new ArrayList<>(listaStringNomes));
            resultado.put("listaIdAmigo", new ArrayList<>(listaIdAmigo));


            return resultado;

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao consultar a lista de solicitações: " + ex.getMessage());
            return null; // Ou você pode lançar uma exceção ou lidar de outra forma com o erro
        } finally {
            fecharRecursos(conexao, statement, resultSet);
        }
    }


    public static void removerListaAmigos(int idUsuario, int idUsuarioAmigo) {
        Connection conexao = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;

        try {
            conexao = obterConexao();

            // Remover amizade para os valores originais
            String sql1 = "DELETE FROM amigos\n" +
                    "WHERE idUsuario = ? AND idUsuarioAmigo = ?";
            statement1 = conexao.prepareStatement(sql1);
            statement1.setInt(1, idUsuario);
            statement1.setInt(2, idUsuarioAmigo);

            // Utilize executeUpdate() para operações de DELETE, UPDATE, INSERT
            int linhasAfetadas1 = statement1.executeUpdate();

            // Remover amizade para os valores trocados
            String sql2 = "DELETE FROM amigos\n" +
                    "WHERE idUsuario = ? AND idUsuarioAmigo = ?";
            statement2 = conexao.prepareStatement(sql2);
            statement2.setInt(1, idUsuarioAmigo);
            statement2.setInt(2, idUsuario);

            // Utilize executeUpdate() novamente
            int linhasAfetadas2 = statement2.executeUpdate();

            int linhasAfetadasTotal = linhasAfetadas1 + linhasAfetadas2;

            if (linhasAfetadasTotal > 0) {
                ModalUtil.getInstance().showModal("Amizade Removida");

            } else {
                System.out.println("Nenhuma amizade foi removida.");
            }

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao remover a amizade: " + ex.getMessage());
        } finally {
            fecharRecursos(conexao, statement1, null);
            fecharRecursos(null, statement2, null);
        }
    }


    public static void removerListaSolicitacao(int idUsuario, int IdSolicitacao) {
        Connection conexao = null;
        PreparedStatement statement = null;

        try {
            conexao = obterConexao();
            String sql = "DELETE FROM solicitacao\n" +
                    "WHERE idUsuario = ? AND IdSolicitacao = ?";
            statement = conexao.prepareStatement(sql);
            statement.setInt(1, idUsuario);
            statement.setInt(2, IdSolicitacao);

            // Utilize executeUpdate() para operações de DELETE, UPDATE, INSERT
            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas > 0) {
                ModalUtil.getInstance().showModal("Solicitação Recusada");
            } else {
                System.out.println("Nenhuma solicitação foi removida.");
            }

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao remover a solicitação: " + ex.getMessage());
        } finally {
            fecharRecursos(conexao, statement, null); // Não é necessário um ResultSet neste caso
        }
    }
    public static void removerListaSolicitacaodois(int idUsuario, int IdSolicitacao) {
        Connection conexao = null;
        PreparedStatement statement = null;

        try {
            conexao = obterConexao();
            String sql = "DELETE FROM solicitacao\n" +
                    "WHERE idUsuario = ? AND IdSolicitacao = ?";
            statement = conexao.prepareStatement(sql);
            statement.setInt(1, idUsuario);
            statement.setInt(2, IdSolicitacao);

            // Utilize executeUpdate() para operações de DELETE, UPDATE, INSERT
            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Solicitação Recusada");
            } else {
                System.out.println("Nenhuma solicitação foi removida.");
            }

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao remover a solicitação: " + ex.getMessage());
        } finally {
            fecharRecursos(conexao, statement, null); // Não é necessário um ResultSet neste caso
        }
    }
    public static void aceitarListaSolicitacao(int idUsuario, int IdSolicitacao, String nome_amigo, int idAmigo, String destinatario) {

        Connection conexao = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;

        try {
            conexao = obterConexao();
            String sql = "INSERT INTO amigos (id, idUsuario, idUsuarioAmigo, nome_amigo, solicitacao) \n" +
                    "VALUES (DEFAULT, ?, ? , ?, ?);\n";

            statement1 = conexao.prepareStatement(sql);

            statement1.setInt(1, idUsuario);
            statement1.setInt(2, idAmigo);
            statement1.setString(3, nome_amigo);
            statement1.setInt(4, IdSolicitacao);

            // Execute a primeira inserção
            int linhasAfetadas1 = statement1.executeUpdate();

            // Crie uma nova instrução para a segunda inserção
            String sqll = "INSERT INTO amigos (id, idUsuario, idUsuarioAmigo, nome_amigo, solicitacao) \n" +
                    "VALUES (DEFAULT, ?, ? , ?, ?);\n";

            statement2 = conexao.prepareStatement(sqll);

            statement2.setInt(1, idAmigo);
            statement2.setInt(2, idUsuario);
            statement2.setString(3, destinatario);
            statement2.setInt(4, IdSolicitacao);

            // Execute a segunda inserção
            int linhasAfetadas2 = statement2.executeUpdate();

            // Verifique se ambas as inserções foram bem-sucedidas
            if (linhasAfetadas1 > 0 && linhasAfetadas2 > 0) {
                ModalUtil.getInstance().showModal("Solicitação aceita!");

            } else {
                System.out.println("Nenhuma solicitação foi aceita.");
            }

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao enviar a solicitação: " + ex.getMessage());
        } finally {
            fecharRecursos(conexao, statement1, null);
            fecharRecursos(conexao, statement2, null);
        }
    }




    public static List<Map<String, Object>> listaUsuarios() {
        Connection conexao = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexao = obterConexao();
            String sql = "SELECT id, nome FROM usuario";
            statement = conexao.prepareStatement(sql);
            resultSet = statement.executeQuery();

            List<Map<String, Object>> listaAmigos = new ArrayList<>();
            while (resultSet.next()) {
                int idUsuario = resultSet.getInt("id");
                String nomeUsuario = resultSet.getString("nome");

                Map<String, Object> usuario = new HashMap<>();
                usuario.put("id", idUsuario);
                usuario.put("nome", nomeUsuario);

                listaAmigos.add(usuario);
            }

            return listaAmigos;
        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao consultar a lista de amigos: " + ex.getMessage());
            // Aqui você pode lançar uma exceção ou lidar de outra forma com o erro
            return null;
        } finally {
            fecharRecursos(conexao, statement, resultSet);
        }
    }


    public static List<Mensagem> obterMensagens(int idUsuario, int idUsuarioAmigo) {
        List<Mensagem> mensagens = new ArrayList<>();
        String query = "SELECT id, idUsuario, idUsuarioAmigo, mensagem FROM mensagem " +
                "WHERE (idUsuario = ? AND idUsuarioAmigo = ?) OR (idUsuario = ? AND idUsuarioAmigo = ?)";

        try (Connection connection = obterConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setInt(2, idUsuarioAmigo);
            preparedStatement.setInt(3, idUsuarioAmigo);
            preparedStatement.setInt(4, idUsuario);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String conteudo = resultSet.getString("mensagem");

                    Mensagem mensagem = new Mensagem(id, idUsuario, idUsuarioAmigo, conteudo);
                    mensagens.add(mensagem);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção adequadamente na sua aplicação
        }

        return mensagens;
    }

    public static List<Map<String, Object>> obterMensagensSimplificado(int idUsuario, int idUsuarioAmigo) {
        List<Map<String, Object>> mensagensSimplificadas = new ArrayList<>();
        String query = "SELECT idUsuario, mensagem FROM mensagem " +
                "WHERE (idUsuario = ? AND idUsuarioAmigo = ?) OR (idUsuario = ? AND idUsuarioAmigo = ?)";

        try (Connection connection = obterConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setInt(2, idUsuarioAmigo);
            preparedStatement.setInt(3, idUsuarioAmigo);
            preparedStatement.setInt(4, idUsuario);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idUsuarioRemetente = resultSet.getInt("idUsuario");
                    String conteudo = resultSet.getString("mensagem");

                    Map<String, Object> mensagemSimplificada = new HashMap<>();
                    mensagemSimplificada.put("idUsuario", idUsuarioRemetente);
                    mensagemSimplificada.put("conteudo", conteudo);
                    mensagensSimplificadas.add(mensagemSimplificada);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção adequadamente na sua aplicação
        }

        return mensagensSimplificadas;
    }



    public static void enviarMensagem(int idUsuario, int idUsuarioAmigo, String mensagem) {
        Connection conexao = null;
        PreparedStatement statement = null;

        try {
            conexao = obterConexao();
            String sql = "INSERT INTO mensagem (idUsuario, idUsuarioAmigo, mensagem) VALUES (?, ?, ?)";
            statement = conexao.prepareStatement(sql);
            statement.setInt(1, idUsuario);
            statement.setInt(2, idUsuarioAmigo);
            statement.setString(3, mensagem);

            // Execute a inserção
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao enviar a mensagem: " + ex.getMessage());
        } finally {
            fecharRecursos(conexao, statement, null);
        }
    }


    protected static void getNomeUser(String nomeDoUsuario) {
    }
}
