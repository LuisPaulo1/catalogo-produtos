package com.catalogoprodutos.config;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.catalogoprodutos.controller.dto.ProductDTO;
import com.catalogoprodutos.controller.exception.StandardError;
import com.catalogoprodutos.controller.openapi.model.PageableModelOpenApi;
import com.catalogoprodutos.controller.openapi.model.ProductsModelOpenApi;
import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SpringFoxConfig implements WebMvcConfigurer {
		
	@Bean
	public Docket apiDocket() {
		
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.OAS_30)
			.select()
				.apis(RequestHandlerSelectors.basePackage("com.catalogoprodutos.controller"))
				.build()
				.apiInfo(apiInfo())
				.tags(tags()[0], tags())		
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())				
				.additionalModels(typeResolver.resolve(StandardError.class))
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.alternateTypeRules(newRule(Page.class, ProductDTO.class, ProductsModelOpenApi.class));
	}
	
	private <T, M, K> AlternateTypeRule newRule(Class<T> returnType, Class<M> modelObject, Class<K> modelObjectOpenApi) {
		var typeResolver = new TypeResolver();
		return AlternateTypeRules.newRule(
				typeResolver.resolve(ResponseEntity.class, typeResolver.resolve(returnType, modelObject)),
				typeResolver.resolve(modelObjectOpenApi),
				Ordered.HIGHEST_PRECEDENCE);		
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Catálogo de produtos")
				.description("API pública para gerenciamento de produtos")
				.version("1.0")				
				.build();
	}	
	
	private Tag[] tags() {
		return new Tag[] {
				new Tag("Produtos", "Gerencia os produtos")								
		};
	}
	
	private List<Response> globalGetResponseMessages() {
		return Arrays.asList(	
			new ResponseBuilder()
				.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
				.description("Erro interno do servidor")				
				.build()
		);			
	}	
	
	private List<Response> globalPostPutResponseMessages() {
		return Arrays.asList(
			new ResponseBuilder()					
				.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
				.description("Requisição inválida (erro do cliente)")
				.representation(MediaType.APPLICATION_JSON ).apply(builderModelProblema())
				.build(),		
			new ResponseBuilder()
				.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
				.description("Erro interno do servidor")
				.build()				
		);			
	}			
	
	private List<Response> globalDeleteResponseMessages() {
		return Arrays.asList(			
			new ResponseBuilder()
				.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))				
				.description("Erro interno do servidor")				
				.build()
		);			
	}	
	
	private Consumer<RepresentationBuilder> builderModelProblema() {
		return r -> r.model(m -> m.name("StandardError")
				.referenceModel(
					ref -> ref.key(
							k -> k.qualifiedModelName(q -> q.name("StandardError").namespace("com.catalogoprodutos.controller.exception")
						))));
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("index.html")
         .addResourceLocations("classpath:/META-INF/resources/");
	}

}
