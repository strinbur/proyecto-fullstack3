package com.grupocordillera.ms_login.migrations;

import com.grupocordillera.ms_login.model.Rol;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.util.List;

@ChangeUnit(id = "v002-init-clients", order = "002", author = "patricio")
public class V002_InitClientsCollection {

    @Execution
    public void execution(MongoTemplate mongoTemplate) {
        createClient(mongoTemplate, "Juan", "Perez", "juan.perez@gmail.com");
        createClient(mongoTemplate, "Maria", "Gonzalez", "maria.gonzalez@gmail.com");
        createClient(mongoTemplate, "Carlos", "Ortiz", "carlos.ortiz@gmail.com");
        createClient(mongoTemplate, "Ana", "Martinez", "ana.martinez@gmail.com");
        createClient(mongoTemplate, "Pedro", "Soto", "pedro.soto@gmail.com");
    }

    private void createClient(MongoTemplate mongoTemplate, String name, String lastname, String email) {
        Document exists = mongoTemplate.getCollection("users").find(new Document("email", email)).first();

        if (exists == null) {
            Document user = new Document();
            user.put("name", name);
            user.put("lastname", lastname);
            user.put("email", email);
            user.put("password", "123456");
            user.put("role", Rol.CLIENTE.name());

            mongoTemplate.getCollection("users").insertOne(user);
        }
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {
        mongoTemplate.getCollection("users").deleteMany(
            new Document("email", new Document("$in", List.of(
                "juan.perez@gmail.com",
                "maria.gonzalez@gmail.com",
                "carlos.ortiz@gmail.com",
                "ana.martinez@gmail.com",
                "pedro.soto@gmail.com"
            )))
        );
    }
}