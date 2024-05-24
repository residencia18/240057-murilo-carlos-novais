package com.resitic.clinica.infra.springdoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfigurations {

    private static final String API_TITLE = "Voll.med API";
    private static final String API_DESCRIPTION = "API Rest da aplicação Voll.med, contendo as funcionalidades de CRUD de médicos e de pacientes, além de agendamento e cancelamento de consultas";
    private static final String CONTACT_NAME = "Time Backend";
    private static final String CONTACT_EMAIL = "backend@voll.med";
    private static final String LICENSE_NAME = "Apache 2.0";
    private static final String LICENSE_URL = "http://voll.med/api/licenca";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .contact(new Contact()
                        .name(CONTACT_NAME)
                        .email(CONTACT_EMAIL))
                .license(new License()
                        .name(LICENSE_NAME)
                        .url(LICENSE_URL));
    }
}
