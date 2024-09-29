package com.example.books.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    private static final String SCHEME_NAME = "bearerAuth";
    private static final String BEARER_FORMAT = "JWT";
    private static final String SCHEME = "bearer";

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8081");
        server.setDescription("book-service");

        Contact contact = new Contact();
        contact.setName("ivanskrobukk@gmail.com");
        contact.setEmail("ivanskrobukk@gmail.com");

        Info info = new Info()
                .title("API системы управления библиотекой")
                .version("1.0")
                .description("Это API предоставляет доступ к управлению библиотекой, включая книги и их доступность.")
                .contact(contact);

        // Возвращаем настроенный OpenAPI объект с информацией и поддержкой безопасности (JWT)
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SCHEME_NAME, new SecurityScheme()
                                .name(SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme(SCHEME)
                                .bearerFormat(BEARER_FORMAT)
                                .in(SecurityScheme.In.HEADER)))
                .info(info)
                .servers(List.of(server));
    }
}
