package com.github.com.screenmatch.models;

import com.github.com.screenmatch.models.enums.Categoria;
import com.github.com.screenmatch.services.ConsultaChatGPT;
import com.github.com.screenmatch.utils.ConsultaMyMemory;
import com.theokanning.openai.OpenAiHttpException;

import java.util.OptionalDouble;

public class Serie {

    private String titulo ;
    private Integer totalTemporadas ;
    private Double avaliacao ;
    private Categoria genero ;
    private String atores ;
    private String poster ;
    private String sinopse ;

    public Serie(DadosSerie dadosSerie) {
        titulo = dadosSerie.titulo();
        totalTemporadas = dadosSerie.totalTemporadas();
        avaliacao = OptionalDouble.of(Double.parseDouble(dadosSerie.avaliacao())).orElse(0.0);
        genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        atores = dadosSerie.atores();
        poster = dadosSerie.poster();
        setSinopse(dadosSerie.sinopse());
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        try {
            this.sinopse = ConsultaChatGPT.obterTraducao(sinopse);
        } catch (OpenAiHttpException e) {
            this.sinopse = ConsultaMyMemory.obterTraducao(sinopse);
        } catch (Exception e) {
            this.sinopse = sinopse;
        }
    }

    @Override
    public String toString() {
        return
               "genero=" + genero +
               ", titulo='" + titulo + '\'' +
               ", totalTemporadas=" + totalTemporadas +
               ", avaliacao=" + avaliacao +
               ", atores='" + atores + '\'' +
               ", poster='" + poster + '\'' +
               ", sinopse='" + sinopse + '\'';
    }
}
