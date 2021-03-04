package com.hiperlogic.hldeliver.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hiperlogic.hldeliver.dto.OrderDTO;
import com.hiperlogic.hldeliver.dto.ProductDTO;
import com.hiperlogic.hldeliver.entities.Order;
import com.hiperlogic.hldeliver.entities.OrderStatus;
import com.hiperlogic.hldeliver.entities.Product;
import com.hiperlogic.hldeliver.repositories.OrderRepository;
import com.hiperlogic.hldeliver.repositories.ProductRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly = true)
	public List<OrderDTO> findAll() {
		List<Order> list = repository.findAllPendingLaterToNewer();
		return list.stream().map(p -> new OrderDTO(p)).collect(Collectors.toList());	
	}
	
	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order order = new Order(null, 
				dto.getAddress(), dto.getLatitude(),
				dto.getLongitude(), Instant.now(),
				OrderStatus.PENDING);
		for(ProductDTO p : dto.getProducts()) {
			Product product = productRepository.getOne(p.getId());
			order.getProducts().add(product);
		}
		
		order = repository.save(order);
		
		return new OrderDTO(order);
	}

	@Transactional
	public OrderDTO setDelivered(Long id) {
		Order order = repository.getOne(id);
		order.setStatus(OrderStatus.DELIVERED);
		order = repository.save(order);
		return new OrderDTO(order);
	}
}
