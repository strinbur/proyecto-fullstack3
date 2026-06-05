package com.grupocordillera.ms_login.migrations;

import com.grupocordillera.ms_login.model.Rol;
import com.mongodb.client.model.IndexOptions;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.util.List;

@ChangeUnit(id = "v001-init-login", order = "001", author = "patricio")
public class V001_InitLoginCollection {

    @Execution
    public void execution(MongoTemplate mongoTemplate) {
        mongoTemplate.getCollection("users").createIndex(
            new Document("email", 1), 
            new IndexOptions().unique(true)
        );

        // Administrador: Patricio
        Document patricioExists = mongoTemplate.getCollection("users").find(new Document("email", "pa.olguine@duocuc.cl")).first();
        if (patricioExists == null) {
            Document patricio = new Document();
            patricio.put("name", "Patricio");
            patricio.put("lastname", "Olguin");
            patricio.put("email", "pa.olguine@duocuc.cl");
            patricio.put("password", "123456");
            patricio.put("role", Rol.ADMIN.name());

            mongoTemplate.getCollection("users").insertOne(patricio);
        }

        // Administrador: Oscar
        Document oscarExists = mongoTemplate.getCollection("users").find(new Document("email", "os.leyton@duocuc.cl")).first();
        if (oscarExists == null) {
            Document oscar = new Document();
            oscar.put("name", "Oscar");
            oscar.put("lastname", "Leyton");
            oscar.put("email", "os.leyton@duocuc.cl");
            oscar.put("password", "123456");
            oscar.put("role", Rol.ADMIN.name());

            mongoTemplate.getCollection("users").insertOne(oscar);
        }

        // Administrador: Benjamin
        Document benjaminExists = mongoTemplate.getCollection("users").find(new Document("email", "benj.vasquezc@duocuc.cl")).first();
        if (benjaminExists == null) {
            Document benjamin = new Document();
            benjamin.put("name", "Benjamin");
            benjamin.put("lastname", "Vasquez");
            benjamin.put("email", "benj.vasquezc@duocuc.cl");
            benjamin.put("password", "123456");
            benjamin.put("role", Rol.ADMIN.name());

            mongoTemplate.getCollection("users").insertOne(benjamin);
        }
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {
        mongoTemplate.getCollection("users").dropIndex("email_1");
        mongoTemplate.getCollection("users").deleteMany(
            new Document("email", new Document("$in", List.of(
                "pa.olguine@duocuc.cl",
                "os.leyton@duocuc.cl",
                "benj.vasquezc@duocuc.cl"
            )))
        );
    }
}