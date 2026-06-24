package com.grupocordillera.ms_reporting.report.repository;


import com.grupocordillera.ms_reporting.report.document.ReportLog;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;


/**
 * Repositorio para el documento {@link ReportLog}.
 */
public interface ReportLogRepository extends MongoRepository<ReportLog, String> {

    /**
     * Recupera todos los registros de reportes ordenados por fecha descendente.
     *
     * @return lista de registros ordenada
     */
    List<ReportLog> findAllByOrderByRequestedAtDesc();
}
