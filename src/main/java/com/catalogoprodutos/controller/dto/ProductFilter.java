package com.catalogoprodutos.controller.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilter {
	
	@ApiModelProperty(value = "name ou description")
	private String q;
	
	@ApiModelProperty(value = "Preço máximo")
	private BigDecimal min_price;
	
	@ApiModelProperty(value = "Preço minímo")
	private BigDecimal max_price;
	
}
