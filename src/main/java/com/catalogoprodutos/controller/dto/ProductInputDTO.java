package com.catalogoprodutos.controller.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductInputDTO {
	
	@ApiModelProperty(example = "iPhone 12", required = true)
	@NotBlank
	private String name;
	
	@ApiModelProperty(example = "iPhone 12 128GB Azul, com Tela de 6,1”.", required = true)
	@NotBlank
	private String description;
	
	@ApiModelProperty(example = "R$ 6000.00", notes = "Valor positivo", required = true)
	@Positive
	@NotNull	
	private BigDecimal price;

}
