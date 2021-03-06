package com.catalogoprodutos.service;

import static com.catalogoprodutos.repository.ProductSpecs.usandoFiltro;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public Page<ProductDTO> listarPorPagina(Pageable pageable) {
		Page<Product> products = repository.findAll(pageable);
		return products.map(product -> new ProductDTO(product));
	}
	
	public List<ProductDTO> listarPorFiltro(ProductFilter productFilter) {
		List<Product> products = repository.findAll(usandoFiltro(productFilter));
		return products.stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
	}

	public ProductDTO buscarPorId(String id) {
		Product product = findProduct(id);		
		return new ProductDTO(product);
	}
	
	@Transactional
	public ProductDTO salvar(ProductInputDTO productInputDTO) {
		Product product = new Product();
		BeanUtils.copyProperties(productInputDTO, product);
		product = repository.save(product);
		return new ProductDTO(product);
	}
	
	@Transactional
	public ProductDTO atualizar(String id, ProductInputDTO productInputDTO) {
		Product productAtual = findProduct(id);		
		BeanUtils.copyProperties(productInputDTO, productAtual);
		productAtual = repository.save(productAtual);
		return new ProductDTO(productAtual);
	}
	
	@Transactional
	public void excluir(String id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException();
		}
	}
	
	private Product findProduct(String id) {
		Product product = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException());
		return product;
	}
	
}
