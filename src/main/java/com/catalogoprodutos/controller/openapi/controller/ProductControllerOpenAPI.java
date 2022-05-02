package com.catalogoprodutos.controller.openapi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.catalogoprodutos.controller.dto.ProductDTO;
import com.catalogoprodutos.controller.dto.ProductFilter;
import com.catalogoprodutos.controller.dto.ProductInputDTO;
import com.catalogoprodutos.controller.exception.StandardError;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags="Produtos")
public interface ProductControllerOpenAPI {
	
	@ApiOperation("Lista os produtos")
	ResponseEntity<List<ProductDTO>> findAll();
	
	@ApiOperation("Lista os produtos por página")
	ResponseEntity<Page<ProductDTO>> findAllByPage(Pageable pageable);
	
	@ApiOperation("Lista os produtos por filtro")
	ResponseEntity<List<ProductDTO>> findByFilter(ProductFilter productFilter);
	
	@ApiOperation("Busca um produto por Id")
	@ApiResponses({         
        @ApiResponse(code = 404, message = "Produto não encontrado")
    })
	ResponseEntity<ProductDTO> findById(@ApiParam(value = "Id do produto") String id);
	
    @ApiOperation("Cadastra um produto")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Produto cadastrado"),
        @ApiResponse(code = 400, message = "Requisição inválida", response = StandardError.class)
    })
	ResponseEntity<ProductDTO> insert(ProductInputDTO productInputDTO); 
	
    @ApiOperation("Atualiza um produto")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Produto atualizado"),
        @ApiResponse(code = 404, message = "Produto não encontrado"),
        @ApiResponse(code = 400, message = "Requisição inválida", response = StandardError.class)
    })
    ResponseEntity<ProductDTO> update(@ApiParam(value = "Id do produto") String id, ProductInputDTO productInputDTO);
	
    @ApiOperation("Exclui um produto por Id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Produto excluído"),        
        @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    ResponseEntity<Void> delete(@ApiParam(value = "Id do produto") String id);
}
