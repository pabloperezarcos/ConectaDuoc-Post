package com.conectaduoc.dto;

public class ScoreResumenDTO {
    private Long idPost;
    private Double promedio;
    private Integer miScore;

    public ScoreResumenDTO(Long idPost, Double promedio, Integer miScore) {
        this.idPost = idPost;
        this.promedio = promedio;
        this.miScore = miScore;
    }

    public Long getIdPost() {
        return idPost;
    }

    public Double getPromedio() {
        return promedio;
    }

    public Integer getMiScore() {
        return miScore;
    }
}