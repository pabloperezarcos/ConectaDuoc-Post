package com.conectaduoc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conectaduoc.exception.ResourceNotFoundException;
import com.conectaduoc.model.PostReport;
import com.conectaduoc.service.PostReportService;

@RestController
@RequestMapping("/api/report")
@Validated
public class PostReportController {

    @Autowired
    private PostReportService reportService;

    // Listar todos los reportes
    @GetMapping
    public ResponseEntity<List<PostReport>> listReports() {
        List<PostReport> reports = reportService.listReports();
        return ResponseEntity.ok(reports);
    }

    // Obtener un reporte por su ID
    @GetMapping("/{id}")
    public ResponseEntity<PostReport> getReport(Long id) {
        PostReport report = reportService.getReport(id)
                .orElseThrow(() -> new ResourceNotFoundException("El reporte con ID " + id + " no fue encontrado."));
        return ResponseEntity.ok(report);
    }

    // Eliminar un reporte por su ID
    @GetMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReport(Long id) {
        // Verifica si el reporte existe, si no, lanza la excepción
        reportService.getReport(id)
                .orElseThrow(() -> new ResourceNotFoundException("El reporte con ID " + id + " no fue encontrado."));

        // Elimina el reporte si existe
        reportService.deleteReport(id);

        return ResponseEntity.noContent().build(); // Devolver 204 No Content
    }

    // Listar reportes por idPost
    @GetMapping("/post/{idPost}")
    public ResponseEntity<List<PostReport>> findByIdPost(Long idPost) {
        List<PostReport> reports = reportService.findByIdPost(idPost);
        if (reports.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron reportes para el post con ID " + idPost);
        }
        return ResponseEntity.ok(reports);
    }

    /* MÉTODOS ADICIONALES */
    @PostMapping("/publicacion")
    public ResponseEntity<PostReport> reportPost(@RequestBody PostReport report) {
        PostReport savedReport = reportService.saveReport(report);
        return ResponseEntity.ok(savedReport);
    }

    @PostMapping("/comentario")
    public ResponseEntity<PostReport> reportComment(@RequestBody PostReport report) {
        PostReport savedReport = reportService.saveReport(report);
        return ResponseEntity.ok(savedReport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostReport> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        PostReport report = reportService.getReport(id)
                .orElseThrow(() -> new ResourceNotFoundException("El reporte con ID " + id + " no fue encontrado."));

        if (body.containsKey("status")) {
            int newStatus = body.get("status");
            if (newStatus < 1 || newStatus > 3) {
                throw new IllegalArgumentException("Estado inválido");
            }
            report.setStatus(newStatus);
        }

        PostReport updated = reportService.saveReport(report);
        return ResponseEntity.ok(updated);
    }

}
