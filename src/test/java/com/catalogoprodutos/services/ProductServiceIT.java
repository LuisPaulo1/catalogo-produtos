package com.catalogoprodutos.services;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.catalogoprodutos.controller.dto.ProductDTO;
import com.catalogoprodutos.repository.ProductRepository;
import com.catalogoprodutos.service.ProductService;
import com.catalogoprodutos.service.exception.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class ProductServiceIT {

	@Autowired
	private ProductService service;
	
	@Autowired
	private ProductRepository repository;

	private String existingId;
	private String nonExistingId;
	private int countTotalProducts;

	@BeforeEach
	void setUp() throws Exception {
		existingId = "1";
		nonExistingId = "100";
		countTotalProducts = 5;
	}

	@Test
	public void listarDeveriaRetornarRecursos() {
		List<ProductDTO> result = service.listar();
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void buscarPorIdDeveriaRetornarProductDTOQuandoIdExistir() {
		
		ProductDTO product = service.buscarPorId(existingId);
		Assertions.assertNotNull(product);
	}
	
	@Test
	public void buscarPorIdDeveriaLancarResourceNotFoundExceptionQuandoIdNaoExistir() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.buscarPorId(nonExistingId);
		});
		
	}
	
	@Test
	public void excluirDeveriaDeletarRecursoQuandoIdExistir() {		
		service.excluir(existingId);
		Assertions.assertEquals(countTotalProducts - 1, repository.count());
	}
	
	@Test
	public void excluirDeveriaLancarResourceNotFoundExceptionQuandoIdNaoExistir() {		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.excluir(nonExistingId);
		});
	}
	
}