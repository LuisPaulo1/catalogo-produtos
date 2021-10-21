package com.catalogoprodutos.repository;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.catalogoprodutos.controller.dto.ProductFilter;
import com.catalogoprodutos.model.Product;

public class ProductSpecs {
	
	public static Specification<Product> usandoFiltro(ProductFilter filtro) {
		return (root, query, builder) -> {
			
			var predicates = new ArrayList<Predicate>();
						
			if(StringUtils.hasText(filtro.getQ())) {
				predicates.add(builder.or(
						builder.like(root.get("name"), "%"+filtro.getQ()+"%"),
						builder.like(root.get("description"), "%"+filtro.getQ()+"%")));
			}
			
			if(filtro.getMin_price() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("price"), filtro.getMin_price()));
			}
			
			if(filtro.getMax_price() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("price"), filtro.getMax_price()));
			}			
			
			return builder.and(predicates.toArray(new Predicate[0]));
			
		};
	}
	

}
