package com.example.myapplication.model;

import java.io.Serializable;

public class Aluno implements Serializable{
    private Long id;
    private String nome;
    private String telefone;
    private String endereco;
    private String site;
    private String foto;
    private Double grau;

    public static class AlunoSerializer implements Serializable {
        private Long id;
        private String nome;
        private String telefone;
        private String endereco;
        private String site;
        private String foto;
        private Double grau;

        public AlunoSerializer(Aluno aluno) {
            this.id = aluno.getId();
            this.nome = aluno.getNome();
            this.telefone = aluno.getTelefone();
            this.endereco = aluno.getEndereco();
            this.site = aluno.getSite();
            this.foto = aluno.getFoto();
            this.grau = aluno.getGrau();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Double getGrau() {
        return grau;
    }

    public void setGrau(Double grau) {
        this.grau = grau;
    }

    @Override
    public String toString() {
        return "Aluno {" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                ", site='" + site + '\'' +
                ", foto='" + foto + '\'' +
                ", grau=" + grau +
                '}';
    }
}
