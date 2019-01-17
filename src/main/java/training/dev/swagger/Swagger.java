package training.dev.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger {
	
	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2) 
				.select().apis(RequestHandlerSelectors.basePackage("training.dev.rest.controllers"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(info());
		
		}
	
	
	private ApiInfo info() {
		return new ApiInfoBuilder()
				.title("REST API")
				.version("5.0")
				.description("Training Purposes")
				.build();
	}

	// Use this to test swagger is working but in JSON format: http://localhost:8080/api/v1/v2/api-docs
	//Use this to test swagger is working but in HTML format http://localhost:8080/api/v1/swagger-ui.html
}
