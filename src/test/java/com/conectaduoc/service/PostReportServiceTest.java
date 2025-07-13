package com.conectaduoc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conectaduoc.model.PostReport;
import com.conectaduoc.repository.PostReportRepository;

@ExtendWith(MockitoExtension.class)
public class PostReportServiceTest {

    @Mock
    private PostReportRepository repository;

    @InjectMocks
    private PostReportService service;

    @Test
    void listReportsTest(){
        List<PostReport> reports = new ArrayList<>();
        PostReport report1 = new PostReport();
        reports.add(report1);
        PostReport report2 = new PostReport();
        reports.add(report2);

        when(repository.findAll()).thenReturn(reports);
        List<PostReport> result = service.listReports();
        assertEquals(2, result.size());
        assertEquals(report1, result.get(0));
        assertEquals(report2, result.get(1));
    }

    public PostReport getReport(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Test
    void getReportTest() {
        Long id = 1L;
        PostReport report = new PostReport();
        report.setIdReport(id);
        when(repository.findById(id)).thenReturn(Optional.of(report));

        PostReport result = service.getReport(id).orElse(null);
        assertEquals(report, result);
    }

    @Test
    void saveReportTest() {
        PostReport report = new PostReport();
        when(repository.save(report)).thenReturn(report);
        PostReport savedReport = service.saveReport(report);
        assertEquals(report, savedReport);
    }

    @Test
    void deleteReportTest() {
        Long id = 1L;
        service.deleteReport(id);
        // Verify that the delete method was called with the correct id
        repository.deleteById(id);
    }

    @Test
    void getCommentsByPostIdTest() {
        Long idPost = 1L;
        List<PostReport> reports = new ArrayList<>();
        PostReport report1 = new PostReport();
        report1.setIdPost(idPost);
        reports.add(report1);
        PostReport report2 = new PostReport();
        report2.setIdPost(idPost);
        reports.add(report2);

        when(repository.findByIdPost(idPost)).thenReturn(reports);
        List<PostReport> result = service.findByIdPost(idPost);
        assertEquals(2, result.size());
        assertEquals(report1, result.get(0));
        assertEquals(report2, result.get(1));
    }

}
