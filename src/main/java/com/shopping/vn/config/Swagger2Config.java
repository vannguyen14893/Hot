package com.shopping.vn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).host("localhost:8080").select()
                .apis(RequestHandlerSelectors.basePackage("com.shopping.vn.controller"))
                .paths(PathSelectors.ant("/*")).build().apiInfo(apiInfo()).useDefaultResponseMessages(false)// override
                // default
                // response
                // message
                .globalResponseMessage(RequestMethod.GET,
                        Collections.singletonList(new ResponseMessageBuilder()
                                .code(HttpStatus.I_AM_A_TEAPOT.value()).message("I'm NOT a teapot!").build()));

    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Pokedex REST API", "Enjoy your pokedex!", "6.9", "http://example.com/",
                new Contact("Hino", "https://github.com/vannguyen14893/Hot", "ducvan14893@gmail.com"),
                "Licence: WTFPL", "http://www.wtfpl.net/txt/copying/", Collections.emptyList());
    }

}
