package com.patrick.games_api.infra.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI gamesApi() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("security", securityScheme()))
                .info(new Info()
                        .title("Games API REST")
                        .description("""
                                API REST de games
                                
                                Esta API permite o cadastro, listagem e gerenciamento (CRUD) de jogos e categorias.
                                Conta com segurança com Spring Security junto com JWT.

                                **Criado por**: Patrick Bastos
                            
                                """)
                        .version("1.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Meu github")
                        .url("https://github.com/patrickxbs"));
    }

   private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .description("Insira um bearer token válido para ser autenticado")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("security");
   }
}


