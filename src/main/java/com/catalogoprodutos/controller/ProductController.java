package com.catalogoprodutos.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.catalogoprodutos.controller.dto.ProductDTO;
import com.catalogoprodutos.controller.dto.ProductFilter;
import com.catalogoprodutos.controller.dto.ProductInputDTO;
import com.catalogoprodutos.controller.openapi.ProductControllerOpenAPI;
import com.catalogoprodutos.service.ProductService;

@RestController
@RequestMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController implements ProductControllerOpenAPI {
	
	@Autowired
	private ProductService service;
	
	@GetMapping
	public ResponseEntity<List<ProductDTO>> findAll() {
		 List<ProductDTO> produtos = service.listar();
		 return ResponseEntity.ok(produtos);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<ProductDTO>> findByFilter(ProductFilter productFilter) {
		List<ProductDTO> produtos = service.listarPorFiltro(productFilter);
		return ResponseEntity.ok(produtos);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable String id) {	
		ProductDTO produto = service.buscarPorId(id);
		return ResponseEntity.ok(produto);		
	}
	
	@PostMapping
	public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductInputDTO productInputDTO) {
		ProductDTO productDTO = service.salvar(productInputDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(productDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable String id, @Valid @RequestBody ProductInputDTO productInputDTO) {		
		ProductDTO productDTO = service.atualizar(id, productInputDTO);
		return ResponseEntity.ok(productDTO);			
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {		
		service.excluir(id);
		return ResponseEntity.ok().build();		
	}
}
