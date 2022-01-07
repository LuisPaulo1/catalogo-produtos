package com.catalogoprodutos.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.catalogoprodutos.controller.dto.ProductDTO;
import com.catalogoprodutos.controller.dto.ProductInputDTO;
import com.catalogoprodutos.model.Product;
import com.catalogoprodutos.repository.ProductRepository;
import com.catalogoprodutos.service.ProductService;
import com.catalogoprodutos.service.exception.ResourceNotFoundException;
import com.catalogoprodutos.util.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;

	private String existingId;
	private String nonExistingId;
	private Product product;
	private ProductInputDTO productInputDTO;
	private List<Product> list;

	@BeforeEach
	void setUp() throws Exception {
		existingId = "1";
		nonExistingId = "2";
		product = Factory.createProduct();
		productInputDTO = Factory.createProductInputDTO();
		list = new ArrayList<>();

		Mockito.when(repository.findAll()).thenReturn(list);

		Mockito.when(repository.save(any())).thenReturn(product);

		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
	}

	@Test
	public void listarDeveriaRetornarRecursos() {

		List<ProductDTO> result = service.listar();

		Assertions.assertNotNull(result);
	}

	@Test
	public void buscarPorIdDeveriaRetornarProductDTOQuandoIdExistir() {

		ProductDTO product = service.buscarPorId(existingId);

		Assertions.assertNotNull(product);

		Mockito.verify(repository, times(1)).findById(existingId);
	}

	@Test
	public void buscarPorIdDeveriaLancarResourceNotFoundExceptionQuandoIdNaoExistir() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.buscarPorId(nonExistingId);
		});

		Mockito.verify(repository, times(1)).findById(nonExistingId);
	}

	@Test
	public void salvarDeveriaRetornarProductDTOQuandoPersistir() {

		ProductDTO result = service.salvar(productInputDTO);
		Assertions.assertNotNull(result);

	}

	@Test
	public void updateDeveriaRetornarProductDTQuandoIdExistir() {

		ProductDTO result = service.atualizar(existingId, productInputDTO);

		Assertions.assertNotNull(result);
	}

	@Test
	public void updateDeveriaLancarResourceNotFoundExceptionQuandoIdNaoExistir() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.atualizar(nonExistingId, productInputDTO);
		});

	}

	@Test
	public void excluirNaoDeveriaLancarExceptionQuandoIdExistir() {

		Assertions.assertDoesNotThrow(() -> {
			service.excluir(existingId);
		});

		Mockito.verify(repository, times(1)).deleteById(existingId);
	}

	@Test
	public void excluirDeveriaLancarResourceNotFoundExceptionQuandoIdNaoExistir() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.excluir(nonExistingId);
		});

		Mockito.verify(repository, times(1)).deleteById(nonExistingId);
	}

}
