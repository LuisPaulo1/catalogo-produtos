package com.catalogoprodutos.models;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.catalogoprodutos.model.Product;

public class ProductTests {
	
	@Test
	public void productDeveriaTerUmaEstruturaCorreta() {
		
		Product product = new Product();
		product.setId("1");
		product.setName("Celular");
		product.setDescription("Apple iPhone 13 Mini 256GB iOS 4G Wi-Fi Tela 5.4 Câmera Dupla 12MP");
		product.setPrice(new BigDecimal(6500));
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertNotNull(product.getName());
		Assertions.assertNotNull(product.getDescription());
		Assertions.assertNotNull(product.getPrice());
		
	}

}
