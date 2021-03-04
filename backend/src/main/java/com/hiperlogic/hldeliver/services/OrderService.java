package com.hiperlogic.hldeliver.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hiperlogic.hldeliver.dto.OrderDTO;
import com.hiperlogic.hldeliver.entities.Order;
import com.hiperlogic.hldeliver.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	@Transactional(readOnly = true)
	public List<OrderDTO> findAll() {
		List<Order> list = repository.findAllPendingLaterToNewer();
		return list.stream().map(p -> new OrderDTO(p)).collect(Collectors.toList());
		
	}
}
