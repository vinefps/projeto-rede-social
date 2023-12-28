package com.example.demo1.Database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Usuario {

    public int id;
    public String nome;
    public String email;
    public String senha;
    public String dtaNascimento;
    public String telefone;
    private int userId = 0;


    public boolean incluirUsuario() {
        String query = "INSERT INTO usuario (nome, email, senha, dtaNascimento, telefone) VALUES (?, ?, ?, ?, ?)";

        Database obDatabase = new Database("usuario");
        this.id = obDatabase.insert(query, this.nome, this.email, this.senha, this.dtaNascimento, this.telefone);
        return true;
    }

    public boolean atualizarUsuario() {
        String query = "UPDATE usuario SET nome = ?, email = ?, senha = ?, dtaNascimento = ?, telefone = ? WHERE id = ?";

        return (new Database("usuario")).update(query, this.nome, this.email, this.senha, this.dtaNascimento, this.telefone, this.id);
    }

    public boolean excluirUsuario() {
        String condition = "id = " + this.id;

        return (new Database("usuario")).delete(condition);
    }

    public static Usuario getUsuario(int id) {
        String query = "SELECT * FROM usuario WHERE id = " + id;

        try {
            ResultSet resultSet = (new Database("usuario")).select(query);
            if (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.id = resultSet.getInt("id");
                usuario.nome = resultSet.getString("nome");
                usuario.email = resultSet.getString("email");
                usuario.senha = resultSet.getString("senha");
                usuario.dtaNascimento = resultSet.getString("dtaNascimento");
                usuario.telefone = resultSet.getString("telefone");
                return usuario;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter usuário: " + e.getMessage());
        }

        return null;
    }



    public Usuario realizarLogin(String email, String senha) {
        String query = "SELECT id, nome, senha FROM usuario WHERE email = ?";

        try (Database obDatabase = new Database("usuario");
             PreparedStatement preparedStatement = obDatabase.getConnection().prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Usuário encontrado no banco de dados, agora compare as senhas
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNome(resultSet.getString("nome"));
                String senhaHashArmazenada = resultSet.getString("senha");

                if (verificarSenha(senha, senhaHashArmazenada)) {
                    return usuario;
                }
            }

            // Se o código chegar aqui, o usuário não foi encontrado ou a senha está incorreta
            return null; // Valor que indica login sem sucesso

        } catch (SQLException e) {
            System.err.println("Erro ao autenticar usuário: " + e.getMessage());
            return null; // Se ocorrer uma exceção, considere o login como não bem-sucedido
        }
    }


    private boolean verificarSenha(String senha, String senhaHashArmazenada) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(senha.getBytes());

            // Converta os bytes do hash para uma representação hexadecimal
            StringBuilder hashStringBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                hashStringBuilder.append(String.format("%02x", b));
            }

            // Compare o hash calculado com o hash armazenado
            return hashStringBuilder.toString().equals(senhaHashArmazenada);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false; // Trate a exceção adequadamente no seu código
        }
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;

    }

    public String getNome() {
        return nome;
    }


}
