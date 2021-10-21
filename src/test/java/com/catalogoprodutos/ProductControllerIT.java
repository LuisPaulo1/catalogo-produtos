package com.catalogoprodutos;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.catalogoprodutos.controller.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

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
		
		Long idExistente = 1L;
		
		ResultActions result =
				mockMvc.perform(get("/products/{id}", idExistente)
					.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpectAll(status().isOk());		
	}
	
	@Test
	void findByIdDeveriaRetornarNotFoundQuandoIdNaoExistir() throws Exception {
		
		Long idInexistente = 100L;
		
		ResultActions result =
				mockMvc.perform(get("/products/{id}", idInexistente)
					.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpectAll(status().isNotFound());		
	}
	
	@Test
	public void insertDeveriaInserirRecursoRetornandoStatus201() throws Exception {
		
		ProductDTO product = new ProductDTO("Mouse", "Mouse gamer", BigDecimal.valueOf(200));
		String jsonBody = objectMapper.writeValueAsString(product);
		
		ResultActions result =
				mockMvc.perform(post("/products")
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
	}
	
	
	@Test
	public void insertDeveriaRetornarBadRequestQuandoCampoNomeNaoInformado() throws Exception {
		
		ProductDTO product = new ProductDTO(" ", "Mouse gamer", BigDecimal.valueOf(200));
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
		
		ProductDTO product = new ProductDTO("Mouse", " ", BigDecimal.valueOf(200));
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
		
		ProductDTO product = new ProductDTO("Mouse", "Mouse gamer", null);
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
		
		ProductDTO product = new ProductDTO("Mouse", "Mouse gamer", BigDecimal.valueOf(-200));
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
		
		Long idExistente = 1L;
		
		ProductDTO product = new ProductDTO("Mouse", "Mouse gamer", BigDecimal.valueOf(200));
		String jsonBody = objectMapper.writeValueAsString(product);
		
		ResultActions result =
				mockMvc.perform(put("/products/{id}", idExistente)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void updateDeveriaRetornarNotFoundQuandoIdNaoExistir() throws Exception {
		
		Long idInexistente = 100L;
		
		ProductDTO product = new ProductDTO("Mouse", "Mouse gamer", BigDecimal.valueOf(200));
		String jsonBody = objectMapper.writeValueAsString(product);
		
		ResultActions result =
				mockMvc.perform(put("/products/{id}", idInexistente)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteDeveriaRetornarStatusOkQuandoIdExistir() throws Exception {
		
		Long idExistente = 1L;
		
		ResultActions result =
				mockMvc.perform(delete("/products/{id}", idExistente));

		result.andExpect(status().isOk());
	}
	
	@Test
	public void deleteDeveriaRetornarNotFoundQuandoIdExistir() throws Exception {
		
		Long idInexistente = 100L;
		
		ResultActions result =
				mockMvc.perform(delete("/products/{id}", idInexistente));

		result.andExpect(status().isNotFound());
	}
	
	

}