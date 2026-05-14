package com.grupocordillera.ms_inventory.migrations;

import com.mongodb.client.model.IndexOptions;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@ChangeUnit(
        id = "v001-init-inventory",
        order = "001",
        author = "patricio"
)
public class V001_InitInventoryCollection {

    @Execution
    public void execution(MongoTemplate mongoTemplate) {

        // CREAR INDICE UNICO PARA CODIGO
        mongoTemplate
                .getCollection("products")
                .createIndex(
                        new Document("codigo", 1),
                        new IndexOptions().unique(true)
                );

        // =========================
        // NOTEBOOK
        // =========================

        Document notebook = new Document();

        notebook.put("codigo", "P001");
        notebook.put("nombre", "Notebook Lenovo IdeaPad");
        notebook.put("marca", "Lenovo");
        notebook.put("precio", 799990.0);
        notebook.put("cantidad", 10);
        notebook.put("categoria", "tecnologia");

        Document atributosNotebook = new Document();

        atributosNotebook.put("procesador", "Intel Core i7");
        atributosNotebook.put("ram", "16GB");
        atributosNotebook.put("almacenamiento", "512GB SSD");
        atributosNotebook.put("pantalla", "15.6 pulgadas");
        atributosNotebook.put("sistemaOperativo", "Windows 11");

        notebook.put("atributos", atributosNotebook);

        // =========================
        // SMART TV SAMSUNG
        // =========================

        Document smartTv = new Document();

        smartTv.put("codigo", "P002");
        smartTv.put("nombre", "Smart TV Samsung");
        smartTv.put("marca", "Samsung");
        smartTv.put("precio", 599990.0);
        smartTv.put("cantidad", 5);
        smartTv.put("categoria", "television");

        Document atributosSmartTv = new Document();

        atributosSmartTv.put("smartTv", true);
        atributosSmartTv.put("pulgadas", 55);
        atributosSmartTv.put("resolucion", "4K");

        smartTv.put("atributos", atributosSmartTv);

        // =========================
        // TV LG NO SMART
        // =========================

        Document tvLg = new Document();

        tvLg.put("codigo", "P003");
        tvLg.put("nombre", "TV LG");
        tvLg.put("marca", "LG");
        tvLg.put("precio", 449990.0);
        tvLg.put("cantidad", 3);
        tvLg.put("categoria", "television");

        Document atributosTvLg = new Document();

        atributosTvLg.put("smartTv", false);
        atributosTvLg.put("pulgadas", 50);
        atributosTvLg.put("resolucion", "4K");

        tvLg.put("atributos", atributosTvLg);

        // INSERTAR PRODUCTOS
        mongoTemplate
                .getCollection("products")
                .insertMany(List.of(
                        notebook,
                        smartTv,
                        tvLg
                ));
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {

        // ELIMINAR INDICE
        mongoTemplate
                .getCollection("products")
                .dropIndex("codigo_1");

        // ELIMINAR PRODUCTOS INSERTADOS
        mongoTemplate
                .getCollection("products")
                .deleteMany(
                        new Document(
                                "codigo",
                                new Document(
                                        "$in",
                                        List.of("P001", "P002", "P003")
                                )
                        )
                );
    }
}