package com.catalogoprodutos.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.catalogoprodutos.controller.dto.ProductInputDTO;
import com.catalogoprodutos.util.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private String existingId;
	private String nonExistingId;
	private ProductInputDTO productInputDTO;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = "1";
		nonExistingId = "100";
		productInputDTO = Factory.createProductInputDTO();
	}

	@Test
	void findAllDeveriaRetornarOsRecursosComStatusOk() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/products")
					.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpectAll(status().isOk());		
	}
	
	@Test
	void findByFilterDeveriaRetornarOsRecursosComStatusOk() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/products/search")
					.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpectAll(status().isOk());		
	}
	
	@Test
	void findByIdDeveriaRetornarStatusOkQuandoIdExistir() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/products/{id}", existingId)
					.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpectAll(status().isOk());		
	}
	
	@Test
	void findByIdDeveriaRetornarNotFoundQuandoIdNaoExistir() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/products/{id}", nonExistingId)
					.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpectAll(status().isNotFound());		
	}
	
	@Test
	public void insertDeveriaInserirRecursoRetornandoStatusCreated() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(productInputDTO);
		
		ResultActions result =
				mockMvc.perform(post("/products")
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
	}
	
	
	@Test
	public void insertDeveriaRetornarBadRequestQuandoCampoNomeNaoInformado() throws Exception {
		
		ProductInputDTO product = new ProductInputDTO(" ", "Mouse gamer", BigDecimal.valueOf(200));
		String jsonBody = objectMapper.writeValueAsString(product);
		
		ResultActions result =
				mockMvc.perform(post("/products")
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isBadRequest());
	}
	
	@Test
	public void insertDeveriaRetornarBadRequestQuandoCampoDescricaoNaoInformado() throws Exception {
		
		ProductInputDTO product = new ProductInputDTO("Mouse", " ", BigDecimal.valueOf(200));
		String jsonBody = objectMapper.writeValueAsString(product);
		
		ResultActions result =
				mockMvc.perform(post("/products")
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isBadRequest());
	}
	
	
	@Test
	public void insertDeveriaRetornarBadRequestQuandoCampoPrecoNaoInformado() throws Exception {
		
		ProductInputDTO product = new ProductInputDTO("Mouse", "Mouse gamer", null);
		String jsonBody = objectMapper.writeValueAsString(product);
		
		ResultActions result =
				mockMvc.perform(post("/products")
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isBadRequest());
	}
	
	@Test
	public void insertDeveriaRetornarBadRequestQuandoCampoPrecoForNegativo() throws Exception {
		
		ProductInputDTO product = new ProductInputDTO("Mouse", "Mouse gamer", BigDecimal.valueOf(-200));
		String jsonBody = objectMapper.writeValueAsString(product);
		
		ResultActions result =
				mockMvc.perform(post("/products")
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateDeveriaAtualizarRecursoQuandoIdExistir() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(productInputDTO);
		
		ResultActions result =
				mockMvc.perform(put("/products/{id}", existingId)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void updateDeveriaRetornarNotFoundQuandoIdNaoExistir() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(productInputDTO);
		
		ResultActions result =
				mockMvc.perform(put("/products/{id}", nonExistingId)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteDeveriaRetornarStatusOkQuandoIdExistir() throws Exception {
		
		ResultActions result =
				mockMvc.perform(delete("/products/{id}", existingId));

		result.andExpect(status().isOk());
	}
	
	@Test
	public void deleteDeveriaRetornarNotFoundQuandoIdNaoExistir() throws Exception {
		
		ResultActions result =
				mockMvc.perform(delete("/products/{id}", nonExistingId));

		result.andExpect(status().isNotFound());
	}

}