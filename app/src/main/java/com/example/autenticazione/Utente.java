package com.example.autenticazione;

public class Utente {


    String emailUtente;
    String nome;
    String token;


    public Utente() {
    }

    public Utente(String emailUtente, String nome, String token) {
        this.emailUtente = emailUtente;
        this.nome = nome;
        this.token = token;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public String getNome() {
        return nome;
    }

    public String getToken() {
        return token;
    }
}

