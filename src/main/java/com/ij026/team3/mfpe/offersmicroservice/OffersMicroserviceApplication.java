package com.ij026.team3.mfpe.offersmicroservice;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class OffersMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OffersMicroserviceApplication.class, args);
	}

	@Bean
	public SimpleDateFormat dateFormat() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return simpleDateFormat;
	}

	@Bean
	public DateTimeFormatter dateTimeFormatter() {
		return DateTimeFormatter.ofPattern("dd-MM-yyyy");
	}

//	@Bean
//	public Docket swaggerConfigration() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.groupName("public-api")
//				.apiInfo(apiInfo())
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("com.cts"))
////					.paths(myPaths())
//				.build();
//	}
//
//	private Predicate<String> myPaths() {
//		return PathSelectors.regex("/api/.*");
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder()
//				.title("employee api")
//				.version("v1.1.0")
//				.contact(new Contact("Subham Santra", "", "subhamsantra2016@gmail.com"))
//				.build();
//	}

	@Bean
	public Docket apiDocket() {

		Docket docket = new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();

		return docket;

	}
}
