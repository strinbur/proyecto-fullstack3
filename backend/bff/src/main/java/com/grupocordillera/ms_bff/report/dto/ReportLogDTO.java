package com.grupocordillera.ms_bff.report.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReportLogDTO {
    /** Identificador del registro en el servicio de reporting. */
    private String id;

    /** Usuario que solicitó el reporte. */
    private String requestedBy;

    /** Fecha y hora de la solicitud. */
    private LocalDateTime requestedAt;

    /** Snapshot del reporte generado en la solicitud. */
    private FullAnalyticsReportDTO snapshot;
}