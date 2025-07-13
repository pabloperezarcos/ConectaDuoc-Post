package com.conectaduoc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conectaduoc.model.PostReport;
import com.conectaduoc.repository.PostReportRepository;

@Service
public class PostReportService {
    
    @Autowired
    private PostReportRepository reportRepository;

    public List<PostReport> listReports() {
        return reportRepository.findAll();
    }

    public Optional<PostReport> getReport(Long id) {
        return reportRepository.findById(id);
    }

    public PostReport saveReport(PostReport report) {
        return reportRepository.save(report);
    }

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }

    public List<PostReport> findByIdPost(Long idPost) {
        return reportRepository.findByIdPost(idPost);
    }
}
