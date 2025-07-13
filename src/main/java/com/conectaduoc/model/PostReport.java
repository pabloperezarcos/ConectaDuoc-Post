package com.conectaduoc.model;

import java.time.LocalDateTime;

import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class PostReport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_report_seq")
    @SequenceGenerator(name = "post_report_seq", sequenceName = "SEQ_POST_REPORT", allocationSize = 1)
    @Column(name = "ID_REPORT")
    private Long idReport;

    @Nullable
    private Long idPost;

    @Nullable
    private Long idComment;

    @Nullable
    private Long idUser;

    @NonNull
    private String reason;

    @NonNull
    private Integer status;

    @NonNull
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    public Long getIdReport() {
        return idReport;
    }

    public void setIdReport(Long idReport) {
        this.idReport = idReport;
    }

    public Long getIdPost() {
        return idPost;
    }

    public void setIdPost(Long idPost) {
        this.idPost = idPost;
    }

    public Long getIdComment() {
        return idComment;
    }

    public void setIdComment(Long idComment) {
        this.idComment = idComment;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
