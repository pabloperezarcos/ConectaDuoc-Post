package com.conectaduoc.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;

@Entity
public class AppPost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ")
    @SequenceGenerator(name = "POST_SEQ", sequenceName = "SEQ_POST", allocationSize = 1)
    @Column(name = "ID_POST")
    private Long idPost;

    @NotNull(message = "El idUser no puede ser nulo")
    private Long idUser;

    @NotNull(message = "El título no puede ser vacío")
    private String title;

    @NotNull(message = "El contenido no puede ser vacío")
    private String content;

    @NotNull(message = "La categoría no puede ser vacía")
    private Long idCategory;

    @NotNull(message = "Las vistas no pueden ser nulas")
    private int views;

    @NotNull(message = "La fecha no puede ser vacía")
    private Date createdDate;

    // Getters y Setters
    public Long getIdPost() {
        return idPost;
    }

    public void setIdPost(Long idPost) {
        this.idPost = idPost;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
