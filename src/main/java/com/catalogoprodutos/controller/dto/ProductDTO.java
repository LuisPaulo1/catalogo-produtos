package com.catalogoprodutos.controller.dto;

import java.math.BigDecimal;

import com.catalogoprodutos.model.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
	
	@ApiModelProperty(example = "1")
	private String id;		
	
	@ApiModelProperty(example = "iPhone 12")
	private String name;	
	
	@ApiModelProperty(example = "iPhone 12 128GB Azul, com Tela de 6,1”.")
	private String description;	
	
	@ApiModelProperty(example = "R$ 6000.00", value = "6000.00")
	private BigDecimal price;
	
	
	public ProductDTO(String name, String description, BigDecimal price) {		
		this.name = name;
		this.description = description;
		this.price = price;
	}	
	
	public ProductDTO(Product entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
	}

}
