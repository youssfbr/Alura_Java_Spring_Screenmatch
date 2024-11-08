package com.github.com.screenmatch.models;

import com.github.com.screenmatch.models.enums.Categoria;
import com.github.com.screenmatch.services.ConsultaChatGPT;
import com.github.com.screenmatch.utils.ConsultaMyMemory;
import com.theokanning.openai.OpenAiHttpException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "tb_series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String titulo ;
    private Integer totalTemporadas ;
    private Double avaliacao ;

    @Enumerated(EnumType.STRING)
    private Categoria genero ;

    private String atores ;
    private String poster ;
    private String sinopse ;

    @Transient
    private final List<Episodio> episodios = new ArrayList<>();

    public Serie(DadosSerie dadosSerie) {
        titulo = dadosSerie.titulo();
        totalTemporadas = dadosSerie.totalTemporadas();
        avaliacao = OptionalDouble.of(Double.parseDouble(dadosSerie.avaliacao())).orElse(0.0);
        genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        atores = dadosSerie.atores();
        poster = dadosSerie.poster();
        setSinopse(dadosSerie.sinopse());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Episodio> getEpisodios() {
        return episodios;
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
