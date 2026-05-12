package com.grupocordillera.ms_login.migrations;

import com.grupocordillera.ms_login.model.Rol;
import com.mongodb.client.model.IndexOptions;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@ChangeUnit(
        id = "v001-init-login",
        order = "001",
        author = "patricio"
)
public class V001_InitLoginCollection {

    @Execution
    public void execution(MongoTemplate mongoTemplate) {

        // Crea un indice unico para correo
        mongoTemplate
                .getCollection("users")
                .createIndex(
                        new Document("correo", 1),
                        new IndexOptions().unique(true)
                );

        // =========================
        // Usuario Patricio
        // =========================

        Document patricioExiste = mongoTemplate
                .getCollection("users")
                .find(new Document("correo", "pa.olguine@duocuc.cl"))
                .first();

        if (patricioExiste == null) {

            Document patricio = new Document();

            patricio.put("nombre", "Patricio");
            patricio.put("apellido", "Olguin");
            patricio.put("correo", "pa.olguine@duocuc.cl");
            patricio.put("password", "123456");
            patricio.put("rol", Rol.ADMIN.name());

            mongoTemplate
                    .getCollection("users")
                    .insertOne(patricio);
        }

        // =========================
        // Usuario Oscar
        // =========================

        Document oscarExiste = mongoTemplate
                .getCollection("users")
                .find(new Document("correo", "os.leyton@duocuc.cl"))
                .first();

        if (oscarExiste == null) {

            Document oscar = new Document();

            oscar.put("nombre", "Oscar");
            oscar.put("apellido", "Leyton");
            oscar.put("correo", "os.leyton@duocuc.cl");
            oscar.put("password", "123456");
            oscar.put("rol", Rol.ADMIN.name());

            mongoTemplate
                    .getCollection("users")
                    .insertOne(oscar);
        }

        // =========================
        // Usuario Benjamin
        // =========================

        Document benjaminExiste = mongoTemplate
                .getCollection("users")
                .find(new Document("correo", "benj.vasquezc@duocuc.cl"))
                .first();

        if (benjaminExiste == null) {

            Document benjamin = new Document();

            benjamin.put("nombre", "Benjamin");
            benjamin.put("apellido", "Vasquez");
            benjamin.put("correo", "benj.vasquezc@duocuc.cl");
            benjamin.put("password", "123456");
            benjamin.put("rol", Rol.ADMIN.name());

            mongoTemplate
                    .getCollection("users")
                    .insertOne(benjamin);
        }
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {

        // Eliminar indice
        mongoTemplate
                .getCollection("users")
                .dropIndex("correo_1");

        // Eliminar usuarios creados
        mongoTemplate
                .getCollection("users")
                .deleteMany(
                        new Document(
                                "correo",
                                new Document(
                                        "$in",
                                        List.of(
                                                "pa.olguine@duocuc.cl",
                                                "os.leyton@duocuc.cl",
                                                "benj.vasquezc@duocuc.cl"
                                        )
                                )
                        )
                );
    }
}