package com.grupocordillera.ms_order.migrations;

import com.grupocordillera.ms_order.model.OrderStatus;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.time.LocalDateTime;
import java.util.List;

@ChangeUnit(id = "v001-init-orders", order = "001", author = "patricio")
public class V001_InitOrdersCollection {

    @Execution
    public void execution(MongoTemplate mongoTemplate) {
        // 1era Orden
        Document order1 = new Document();
        order1.put("userEmail", "juan.perez@gmail.com");
        order1.put("userName", "Juan Perez");
        order1.put("status", OrderStatus.COMPLETADO.name());
        order1.put("createdAt", LocalDateTime.now().minusDays(15));
        order1.put("total", 799990.0);
        order1.put("items", List.of(createItem("P001", "Notebook Lenovo IdeaPad", 799990.0, 1, "tecnologia")));

        // 2da Orden
        Document order2 = new Document();
        order2.put("userEmail", "maria.gonzalez@gmail.com");
        order2.put("userName", "Maria Gonzalez");
        order2.put("status", OrderStatus.COMPLETADO.name());
        order2.put("createdAt", LocalDateTime.now().minusDays(10));
        order2.put("total", 1199980.0);
        order2.put("items", List.of(createItem("P002", "Smart TV Samsung", 599990.0, 2, "television")));

        // 3era Orden
        Document order3 = new Document();
        order3.put("userEmail", "pedro.soto@gmail.com");
        order3.put("userName", "Pedro Soto");
        order3.put("status", OrderStatus.PENDIENTE.name());
        order3.put("createdAt", LocalDateTime.now().minusDays(5));
        order3.put("total", 1249970.0);
        order3.put("items", List.of(
            createItem("P001", "Notebook Lenovo IdeaPad", 799990.0, 1, "tecnologia"),
            createItem("P003", "TV LG", 449990.0, 1, "television")
        ));

        // 4ta Orden
        Document order4 = new Document();
        order4.put("userEmail", "ana.martinez@gmail.com");
        order4.put("userName", "Ana Martinez");
        order4.put("status", OrderStatus.CANCELADO.name());
        order4.put("createdAt", LocalDateTime.now().minusDays(3));
        order4.put("total", 449990.0);
        order4.put("items", List.of(createItem("P003", "TV LG", 449990.0, 1, "television")));

        // 5ta Orden
        Document order5 = new Document();
        order5.put("userEmail", "carlos.ortiz@gmail.com");
        order5.put("userName", "Carlos Ortiz");
        order5.put("status", OrderStatus.COMPLETADO.name());
        order5.put("createdAt", LocalDateTime.now().minusDays(1));
        order5.put("total", 1849970.0);
        order5.put("items", List.of(
            createItem("P001", "Notebook Lenovo IdeaPad", 799990.0, 1, "tecnologia"),
            createItem("P002", "Smart TV Samsung", 599990.0, 1, "television"),
            createItem("P003", "TV LG", 449990.0, 1, "television")
        ));

        mongoTemplate.getCollection("orders").insertMany(List.of(order1, order2, order3, order4, order5));
    }

    private Document createItem(String code, String name, double price, int quantity, String category) {
        Document item = new Document();
        item.put("productCode", code);
        item.put("name", name);
        item.put("price", price);
        item.put("quantity", quantity);
        item.put("category", category);
        item.put("subtotal", price * quantity);
        return item;
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {
        mongoTemplate.getCollection("orders").deleteMany(
            new Document("userEmail", new Document("$in", List.of(
                "juan.perez@gmail.com",
                "maria.gonzalez@gmail.com",
                "pedro.soto@gmail.com",
                "ana.martinez@gmail.com",
                "carlos.ortiz@gmail.com"
            )))
        );
    }
}