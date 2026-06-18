package com.grupocordillera.ms_data_aggregation.dataset.controller;

import com.grupocordillera.ms_data_aggregation.dataset.dto.CombinedDatasetDTO;
import com.grupocordillera.ms_data_aggregation.dataset.service.DatasetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatasetController {

    private final DatasetService datasetService;

    public DatasetController(DatasetService datasetService) {
        this.datasetService = datasetService;
    }

    @GetMapping("/dataset")
    public CombinedDatasetDTO getDataset() {
        return datasetService.getDataset();
    }
}