package io.swagger.configuration;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;

import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-03-23T11:29:25.537+05:30[Asia/Kolkata]")
@Configuration
public class SwaggerDocumentationConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("InterMine Web Services")
            .description("InterMine Web Services specifications.")
            .license("LGPL 2.1")
            .licenseUrl("https://github.com/intermine/intermine/blob/dev/LICENSE")
            .termsOfServiceUrl("http://intermine.org/")
            .version("1.0.0")
            .contact(new Contact("","", "info@intermine.org"))
            .build();
    }

    @Bean
    public Docket customImplementation(){
        List<SecurityScheme> schemeList = new ArrayList<>();
        schemeList.add(new BasicAuth("BasicAuth"));
        schemeList.add(new ApiKey("ApiKeyAuthToken","token", ApiKeyVehicle.QUERY_PARAM.getValue()));
        schemeList.add(new ApiKey("JWTBearerAuth", "Authorization", "header"));
        return new Docket(DocumentationType.SWAGGER_2)
                .tags(new Tag("Version", ""),
                        new Tag("Data Model", ""),
                        new Tag("Summary Fields", ""),
                        new Tag("Key Fields", ""),
                        new Tag("Branding", ""),
                        new Tag("Schemata", ""),
                        new Tag("FacetSearch", ""),
                        new Tag("User Creation", ""),
                        new Tag("Semantic Markup", ""),
                        new Tag("Templates (System)", ""),
                        new Tag("FacetList", ""),
                        new Tag("Session", ""),
                        new Tag("WebProperties", ""),
                        new Tag("Permanent URL", ""),
                        new Tag("Sequence Access", ""),
                        new Tag("Generated Code", ""),
                        new Tag("QuickSearch", ""),
                        new Tag("JBrowse Simple Data Service", ""),
                        new Tag("Templates", ""),
                        new Tag("Token", ""),
                        new Tag("Who-Am-I?", ""),
                        new Tag("Lists", ""),
                        new Tag("List Renaming", ""),
                        new Tag("Append to List", ""),
                        new Tag("List Tags", ""),
                        new Tag("User Preferences", ""),
                        new Tag("Tokens", ""),
                        new Tag("Permanent Token", ""),
                        new Tag("Saved Template", ""),
                        new Tag("Template Tags", ""),
                        new Tag("Users", ""),
                        new Tag("User", ""),
                        new Tag("Deregistration Tokens", ""),
                        new Tag("Template Upload", ""),
                        new Tag("Create List from Query", ""),
                        new Tag("Add to List from Query", ""),
                        new Tag("Template Results", ""),
                        new Tag("Template to list", ""),
                        new Tag("Append to List from Template Results", ""),
                        new Tag("Saved Query", ""),
                        new Tag("Saved Queries", ""),
                        new Tag("Save Query", ""),
                        new Tag("Possible Values", ""),
                        new Tag("Id Resolution", ""),
                        new Tag("List Sharing", ""),
                        new Tag("List Invitations", ""),
                        new Tag("Widgets", ""),
                        new Tag("Query Store", ""),
                        new Tag("Query Results", ""),
                        new Tag("Find Lists Containing an Object", ""),
                        new Tag("Jaccard Index", ""),
                        new Tag("List Union", ""),
                        new Tag("List Substraction", ""),
                        new Tag("List Intersection", ""),
                        new Tag("List Difference", "")
                        )
                .select()
                    .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                    .build()
                .directModelSubstitute(org.threeten.bp.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.threeten.bp.OffsetDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo())
                .securitySchemes(schemeList);
    }

}
