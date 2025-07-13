package com.conectaduoc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "POST_CATEGORY")
public class PostCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_category_seq")
    @SequenceGenerator(name = "post_category_seq", sequenceName = "SEQ_POST_CATEGORY", allocationSize = 1)
    @Column(name = "ID_CATEGORY")
    private Long idCategory;

    @NotNull(message = "El nombre no puede estar vacío")
    @Column(name = "NAME")
    private String name;

    @NotNull(message = "La descripción no puede estar vacía")
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull(message = "El estado no puede estar vacío")
    @Column(name = "STATUS")
    private Integer status;

    public PostCategory() {
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}