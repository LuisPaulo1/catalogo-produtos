package com.catalogoprodutos.util;

import java.math.BigDecimal;

import com.catalogoprodutos.controller.dto.ProductDTO;
import com.catalogoprodutos.controller.dto.ProductInputDTO;
import com.catalogoprodutos.model.Product;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product("1", "Phone", "Apple iPhone 13 Mini 256GB", BigDecimal.valueOf(800.0));
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product);
	}
	
	public static ProductInputDTO createProductInputDTO() {
		ProductInputDTO product = new ProductInputDTO("Phone", "Apple iPhone 13 Mini 256GB", BigDecimal.valueOf(800.0));
		return product;
	}
}