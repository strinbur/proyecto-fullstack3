package com.grupocordillera.ms_reporting.report.document;


import com.grupocordillera.ms_reporting.report.dto.FullAnalyticsReportDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;


@Data
@Document(collection = "report_logs")
public class ReportLog {


    @Id
    private String id;


    private String requestedBy;

    private LocalDateTime requestedAt;

    private FullAnalyticsReportDTO snapshot;
}
