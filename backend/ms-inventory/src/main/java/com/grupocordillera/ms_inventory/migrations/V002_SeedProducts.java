package com.grupocordillera.ms_inventory.migrations;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@ChangeUnit(
        id = "v002-seed-products",
        order = "002",
        author = "patricio"
)
public class V002_SeedProducts {

    @Execution
    public void execution(MongoTemplate mongoTemplate) {

        // =========================
        // NOTEBOOKS (3)
        // =========================

        Document nb1Specs = new Document();
        nb1Specs.put("procesador", "Intel Core i5 13th Gen");
        nb1Specs.put("ram", "16GB");
        nb1Specs.put("almacenamiento", "512GB SSD");
        nb1Specs.put("pantalla", "14 pulgadas");

        Document nb1 = new Document();
        nb1.put("code", "P004");
        nb1.put("name", "Dell Inspiron 14");
        nb1.put("brand", "Dell");
        nb1.put("price", 699990.0);
        nb1.put("quantity", 8);
        nb1.put("category", "tecnologia");
        nb1.put("specs", nb1Specs);

        Document nb2Specs = new Document();
        nb2Specs.put("procesador", "AMD Ryzen 7 7735HS");
        nb2Specs.put("ram", "16GB");
        nb2Specs.put("almacenamiento", "1TB SSD");
        nb2Specs.put("pantalla", "15.6 pulgadas");

        Document nb2 = new Document();
        nb2.put("code", "P005");
        nb2.put("name", "Lenovo Legion 5");
        nb2.put("brand", "Lenovo");
        nb2.put("price", 1099990.0);
        nb2.put("quantity", 5);
        nb2.put("category", "tecnologia");
        nb2.put("specs", nb2Specs);

        Document nb3Specs = new Document();
        nb3Specs.put("procesador", "Apple M2");
        nb3Specs.put("ram", "8GB");
        nb3Specs.put("almacenamiento", "256GB SSD");
        nb3Specs.put("pantalla", "13.6 pulgadas");

        Document nb3 = new Document();
        nb3.put("code", "P006");
        nb3.put("name", "MacBook Air M2");
        nb3.put("brand", "Apple");
        nb3.put("price", 1299990.0);
        nb3.put("quantity", 4);
        nb3.put("category", "tecnologia");
        nb3.put("specs", nb3Specs);

        // =========================
        // CELULARES (4)
        // =========================

        Document c1Specs = new Document();
        c1Specs.put("pantalla", "6.1 pulgadas");
        c1Specs.put("camara", "48MP");
        c1Specs.put("bateria", "3200mAh");
        c1Specs.put("almacenamiento", "128GB");

        Document c1 = new Document();
        c1.put("code", "P007");
        c1.put("name", "iPhone 15");
        c1.put("brand", "Apple");
        c1.put("price", 999990.0);
        c1.put("quantity", 10);
        c1.put("category", "celulares");
        c1.put("specs", c1Specs);

        Document c2Specs = new Document();
        c2Specs.put("pantalla", "6.6 pulgadas");
        c2Specs.put("camara", "50MP");
        c2Specs.put("bateria", "5000mAh");
        c2Specs.put("almacenamiento", "256GB");

        Document c2 = new Document();
        c2.put("code", "P008");
        c2.put("name", "Samsung Galaxy S24");
        c2.put("brand", "Samsung");
        c2.put("price", 899990.0);
        c2.put("quantity", 12);
        c2.put("category", "celulares");
        c2.put("specs", c2Specs);

        Document c3Specs = new Document();
        c3Specs.put("pantalla", "6.7 pulgadas");
        c3Specs.put("camara", "108MP");
        c3Specs.put("bateria", "5100mAh");
        c3Specs.put("almacenamiento", "256GB");

        Document c3 = new Document();
        c3.put("code", "P009");
        c3.put("name", "Xiaomi 13 Pro");
        c3.put("brand", "Xiaomi");
        c3.put("price", 749990.0);
        c3.put("quantity", 15);
        c3.put("category", "celulares");
        c3.put("specs", c3Specs);

        Document c4Specs = new Document();
        c4Specs.put("pantalla", "6.4 pulgadas");
        c4Specs.put("camara", "64MP");
        c4Specs.put("bateria", "4500mAh");
        c4Specs.put("almacenamiento", "128GB");

        Document c4 = new Document();
        c4.put("code", "P010");
        c4.put("name", "Google Pixel 8");
        c4.put("brand", "Google");
        c4.put("price", 799990.0);
        c4.put("quantity", 9);
        c4.put("category", "celulares");
        c4.put("specs", c4Specs);

        // =========================
        // AUDIFONOS PREMIUM (3)
        // =========================

        Document h1Specs = new Document();
        h1Specs.put("tipo", "over-ear");
        h1Specs.put("cancelacionRuido", true);
        h1Specs.put("conexion", "Bluetooth 5.3");
        h1Specs.put("autonomia", "30h");

        Document h1 = new Document();
        h1.put("code", "P011");
        h1.put("name", "Sony WH-1000XM5");
        h1.put("brand", "Sony");
        h1.put("price", 399990.0);
        h1.put("quantity", 20);
        h1.put("category", "audio");
        h1.put("specs", h1Specs);

        Document h2Specs = new Document();
        h2Specs.put("tipo", "over-ear");
        h2Specs.put("cancelacionRuido", true);
        h2Specs.put("conexion", "Bluetooth 5.0");
        h2Specs.put("autonomia", "20h");

        Document h2 = new Document();
        h2.put("code", "P012");
        h2.put("name", "Bose QuietComfort Ultra");
        h2.put("brand", "Bose");
        h2.put("price", 429990.0);
        h2.put("quantity", 14);
        h2.put("category", "audio");
        h2.put("specs", h2Specs);

        Document h3Specs = new Document();
        h3Specs.put("tipo", "over-ear");
        h3Specs.put("cancelacionRuido", true);
        h3Specs.put("conexion", "Bluetooth 5.0");
        h3Specs.put("autonomia", "24h");

        Document h3 = new Document();
        h3.put("code", "P013");
        h3.put("name", "Apple AirPods Max");
        h3.put("brand", "Apple");
        h3.put("price", 599990.0);
        h3.put("quantity", 6);
        h3.put("category", "audio");
        h3.put("specs", h3Specs);

        // =========================
        // INSERT
        // =========================
        mongoTemplate.getCollection("products")
                .insertMany(List.of(
                        nb1, nb2, nb3,
                        c1, c2, c3, c4,
                        h1, h2, h3
                ));
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {

        mongoTemplate.getCollection("products").deleteMany(
                new Document("code",
                        new Document("$in", List.of(
                                "P004","P005","P006",
                                "P007","P008","P009","P010",
                                "P011","P012","P013"
                        ))
                )
        );
    }
}