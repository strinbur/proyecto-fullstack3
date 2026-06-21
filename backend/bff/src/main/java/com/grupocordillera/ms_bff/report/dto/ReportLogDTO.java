package com.grupocordillera.ms_bff.report.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReportLogDTO {

    private String id;
    private String requestedBy;
    private LocalDateTime requestedAt;
    private FullAnalyticsReportDTO snapshot;
}