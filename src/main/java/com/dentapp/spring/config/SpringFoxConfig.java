package com.dentapp.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.service.contexts.SecurityContext;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;


//@Profile({"prod && !dev"})
@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage( "com.dentapp.spring" ) )
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(securityContext());
    }

    private ApiKey apiKey() {
        return new ApiKey("apiKey", "Authorization", "header");
    }

    private List<SecurityContext> securityContext() {
        return List.of(SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()).build());
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("apiKey",
                authorizationScopes));
    }
}