package com.example.authservice;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8083");
        server.setDescription("jwt");

        Contact myContact = new Contact();
        myContact.setName("ivanskrobukk@gmail.com");
        myContact.setEmail("ivanskrobukk@gmail.com");

        Info information = new Info()
                .title("Генерация JWT для Library")
                .version("1.0")
                .description("Это API предоставляет доступ к управлению библиотекой, включая книги и их доступность.")
                .contact(myContact);

        return new OpenAPI().info(information).servers(List.of(server));
    }
}
