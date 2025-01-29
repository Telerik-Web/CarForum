package com.telerikacademy.web.forumsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Forum System")
                        .version("1.0")
                        .description("Forum System for everything automotive. With users who are able to create a post, " +
                                "update a post and delete a post. With some users being admins and thus allowing them to " +
                                "update other users posts and promote, block or delete other users."));
    }
}
