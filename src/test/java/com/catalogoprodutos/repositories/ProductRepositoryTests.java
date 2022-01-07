package com.catalogoprodutos.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.catalogoprodutos.model.Product;
import com.catalogoprodutos.repository.ProductRepository;
import com.catalogoprodutos.util.Factory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests {

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
	public void saveDeveriaPersistirComAutoIncrementoQuandoIdNulo() {

		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		Optional<Product> result = repository.findById(product.getId());
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, Integer.valueOf(product.getId()));
		Assertions.assertTrue(result.isPresent());
		Assertions.assertSame(result.get(), product);
	}
	
	@Test
	public void deleteDeveriaDeletarProductQuandoIdExistir() {
		
		repository.deleteById(existingId);

		Optional<Product> result = repository.findById(existingId);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteDeveriaLancaResourceNotFoundExceptionQuandoIdNaoExistir() {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
	}
	
	@Test
	public void findByIdDeveriaRetornarProductQuandoIdExistir() {
		
		Optional<Product> result = repository.findById(existingId);
		
		Assertions.assertTrue(result.isPresent());
	}
	
	@Test
	public void findByIdNaoDeveriaRetornarProductQuandoIdNaoExistir() {
		
		Optional<Product> result = repository.findById(nonExistingId);
		
		Assertions.assertFalse(result.isPresent());
	}
	
}
