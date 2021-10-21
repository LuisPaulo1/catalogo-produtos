package com.catalogoprodutos.controller.exception;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(example = "400", position = 1)
	private Integer status_code;	
	
	@ApiModelProperty(example = "Requisição inválida.", position = 2)
	private String message;
	
}
