package com.estu.petify.petifycore.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket petifyApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getPetifyApiInformation());
    }

    private ApiInfo getPetifyApiInformation() {

        return new ApiInfoBuilder()
                .title("PETIFY API")
                .version("release 1.0")
                .description("API - Petify Application")
                .contact(new Contact("Batuhan Kiraz", "https://github.com/batuhankiraz", "bthnkrz@petify.com"))
                .license("Apache License Version 2.0")
                .build();
    }
}
