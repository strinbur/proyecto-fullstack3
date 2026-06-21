package com.grupocordillera.ms_inventory.migrations;

import com.mongodb.client.model.IndexOptions;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@ChangeUnit(
        id = "v001-init-inventory-clean",
        order = "001",
        author = "patricio"
)
public class V001_InitInventoryCollection {

    @Execution
    public void execution(MongoTemplate mongoTemplate) {

        // =========================
        // INDEXES
        // =========================
        mongoTemplate.getCollection("products")
                .createIndex(new Document("code", 1), new IndexOptions().unique(true));

        mongoTemplate.getCollection("products")
                .createIndex(new Document("category", 1));

        mongoTemplate.getCollection("products")
                .createIndex(new Document("brand", 1));

        // =========================
        // PRODUCT 1 - TECNOLOGIA
        // =========================
        Document notebookSpecs = new Document();
        notebookSpecs.put("procesador", "Intel Core i7");
        notebookSpecs.put("ram", "16GB");
        notebookSpecs.put("almacenamiento", "512GB SSD");
        notebookSpecs.put("pantalla", "15.6 pulgadas");
        notebookSpecs.put("sistemaOperativo", "Windows 11");
        notebookSpecs.put("garantia", "12 meses");

        Document notebook = new Document();
        notebook.put("code", "P001");
        notebook.put("name", "Notebook Lenovo IdeaPad");
        notebook.put("brand", "Lenovo");
        notebook.put("price", 799990.0);
        notebook.put("quantity", 10);
        notebook.put("category", "tecnologia");
        notebook.put("specs", notebookSpecs);

        // =========================
        // PRODUCT 2 - TELEVISOR SAMSUNG
        // =========================
        Document smartTvSpecs = new Document();
        smartTvSpecs.put("smartTv", true);
        smartTvSpecs.put("pulgadas", 55);
        smartTvSpecs.put("resolucion", "4K");

        Document smartTv = new Document();
        smartTv.put("code", "P002");
        smartTv.put("name", "Smart TV Samsung 55\"");
        smartTv.put("brand", "Samsung");
        smartTv.put("price", 599990.0);
        smartTv.put("quantity", 5);
        smartTv.put("category", "television");
        smartTv.put("specs", smartTvSpecs);

        // =========================
        // PRODUCT 3 - TELEVISOR LG (CON STOCK)
        // =========================
        Document tvLgSpecs = new Document();
        tvLgSpecs.put("smartTv", false);
        tvLgSpecs.put("pulgadas", 50);
        tvLgSpecs.put("resolucion", "4K");

        Document tvLg = new Document();
        tvLg.put("code", "P003");
        tvLg.put("name", "TV LG 50\"");
        tvLg.put("brand", "LG");
        tvLg.put("price", 449990.0);
        tvLg.put("quantity", 5);
        tvLg.put("category", "television");
        tvLg.put("specs", tvLgSpecs);

        // =========================
        // INSERT DATA
        // =========================
        mongoTemplate.getCollection("products")
                .insertMany(List.of(notebook, smartTv, tvLg));
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {

        mongoTemplate.getCollection("products").dropIndexes();

        mongoTemplate.getCollection("products").deleteMany(
                new Document("code",
                        new Document("$in", List.of("P001", "P002", "P003"))
                )
        );
    }
}