package com.hiperlogic.hldeliver.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hiperlogic.hldeliver.dto.ProductDTO;
import com.hiperlogic.hldeliver.entities.Product;
import com.hiperlogic.hldeliver.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll() {
		List<Product> list = repository.findAllByOrderByNameAsc();
		return list.stream().map(p -> new ProductDTO(p)).collect(Collectors.toList());
		
	}
}
