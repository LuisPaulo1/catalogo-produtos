package com.catalogoprodutos.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.catalogoprodutos.controller.ProductController;
import com.catalogoprodutos.controller.dto.ProductDTO;
import com.catalogoprodutos.controller.dto.ProductInputDTO;
import com.catalogoprodutos.service.ProductService;
import com.catalogoprodutos.service.exception.ResourceNotFoundException;
import com.catalogoprodutos.util.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService service;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private String existingId;
	private String nonExistingId;
	private ProductDTO productDTO;
	private ProductInputDTO productInputDTO;
	private List<ProductDTO> list;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = "1";
		nonExistingId = "100";
		productDTO = Factory.createProductDTO();
		productInputDTO = Factory.createProductInputDTO();
		list = new ArrayList<>();
		
		when(service.listar()).thenReturn(list);
		when(service.listarPorFiltro(any())).thenReturn(list);

		when(service.buscarPorId(existingId)).thenReturn(productDTO);
		when(service.buscarPorId(nonExistingId)).thenThrow(ResourceNotFoundException.class);

		when(service.salvar(any())).thenReturn(productDTO);
		
		when(service.atualizar(eq(existingId), any())).thenReturn(productDTO);
		when(service.atualizar(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
		
		doNothing().when(service).excluir(existingId);
		doThrow(ResourceNotFoundException.class).when(service).excluir(nonExistingId);
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
