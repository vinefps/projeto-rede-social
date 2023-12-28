package com.example.demo1.Database;

public class Mensagem {
    private int id;
    private int idUsuario;
    private int idUsuarioAmigo;
    private String conteudo;

    public Mensagem(int id, int idUsuario, int idUsuarioAmigo, String conteudo) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idUsuarioAmigo = idUsuarioAmigo;
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public int getIdUsuarioAmigo() {
        return idUsuarioAmigo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }



}
