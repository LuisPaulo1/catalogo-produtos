package com.catalogoprodutos.service;

import static com.catalogoprodutos.repository.ProductSpecs.usandoFiltro;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalogoprodutos.controller.dto.ProductDTO;
import com.catalogoprodutos.controller.dto.ProductFilter;
import com.catalogoprodutos.controller.dto.ProductInputDTO;
import com.catalogoprodutos.model.Product;
import com.catalogoprodutos.repository.ProductRepository;
import com.catalogoprodutos.service.exception.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public List<ProductDTO> listar() {
		List<Product> products = repository.findAll();
		return products.stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
	}
	
	public List<ProductDTO> listarPorFiltro(ProductFilter productFilter) {
		List<Product> products = repository.findAll(usandoFiltro(productFilter));
		return products.stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
	}

	public ProductDTO buscarPorId(String id) throws ResourceNotFoundException {		
		Optional<Product> product = repository.findById(id);
		
		if(product.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		return new ProductDTO(product.get());			
	}
	
	@Transactional
	public ProductDTO salvar(ProductInputDTO productInputDTO) {
		Product product = new Product();
		BeanUtils.copyProperties(productInputDTO, product);
		product = repository.save(product);
		return new ProductDTO(product);
	}
	
	@Transactional
	public ProductDTO atualizar(String id, ProductInputDTO productInputDTO) throws ResourceNotFoundException {
		Optional<Product> productAtual = repository.findById(id);
		
		if(productAtual.isEmpty()) {
			throw new ResourceNotFoundException();
		}		
		
		Product product = productAtual.get();
		BeanUtils.copyProperties(productInputDTO, product);
		product = repository.save(product);
		return new ProductDTO(product);
	}
	
	@Transactional
	public void excluir(String id) throws ResourceNotFoundException {		
		Optional<Product> product = repository.findById(id);
		
		if(product.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		repository.deleteById(id);		
	}	
	
}
