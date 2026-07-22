package com.ec3.biblioteca.prestamos.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI prestamosOpenApi() {
        String schemeName = "keycloak";
        return new OpenAPI()
                .info(new Info()
                        .title("API de Préstamos - EC3")
                        .version("1.0.0")
                        .description("Préstamos de biblioteca con comunicación OpenFeign y seguridad OAuth2"))
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .components(new Components().addSecuritySchemes(schemeName,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows().authorizationCode(
                                        new OAuthFlow()
                                                .authorizationUrl("http://localhost:8090/realms/biblioteca/protocol/openid-connect/auth")
                                                .tokenUrl("http://localhost:8090/realms/biblioteca/protocol/openid-connect/token")
                                                .scopes(new Scopes().addString("openid", "OpenID Connect"))))));
    }
}
