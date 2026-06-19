package com.grupocordillera.ms_reporting.report.repository;


import com.grupocordillera.ms_reporting.report.document.ReportLog;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;


public interface ReportLogRepository extends MongoRepository<ReportLog, String> {


    List<ReportLog> findAllByOrderByRequestedAtDesc();
}
