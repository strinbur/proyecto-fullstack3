package com.grupocordillera.ms_reporting.report.document;


import com.grupocordillera.ms_reporting.report.dto.FullAnalyticsReportDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;


/**
 * Documento que representa un registro de ejecución de un reporte analítico.
 */
@Data
@Document(collection = "report_logs")
public class ReportLog {

    /**
     * Identificador del registro en MongoDB.
     */
    @Id
    private String id;

    /**
     * Identificador del usuario que solicitó el reporte.
     */
    private String requestedBy;

    /**
     * Fecha y hora en que se solicitó el reporte.
     */
    private LocalDateTime requestedAt;

    /**
     * Snapshot del reporte generado en el momento de la solicitud.
     */
    private FullAnalyticsReportDTO snapshot;
}
